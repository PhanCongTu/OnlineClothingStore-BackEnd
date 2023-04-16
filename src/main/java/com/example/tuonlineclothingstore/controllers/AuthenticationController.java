package com.example.tuonlineclothingstore.controllers;

import com.example.tuonlineclothingstore.dtos.Login;
import com.example.tuonlineclothingstore.dtos.SignUp;
import com.example.tuonlineclothingstore.dtos.TokenDetails;
import com.example.tuonlineclothingstore.dtos.User.UserDto;
import com.example.tuonlineclothingstore.entities.User;
import com.example.tuonlineclothingstore.exceptions.InvalidException;
import com.example.tuonlineclothingstore.exceptions.UserNotFoundAuthenticationException;
import com.example.tuonlineclothingstore.securities.CustomUserDetailsService;
import com.example.tuonlineclothingstore.securities.JwtTokenUtils;
import com.example.tuonlineclothingstore.securities.JwtUserDetails;
import com.example.tuonlineclothingstore.securities.UserAuthenticationToken;
import com.example.tuonlineclothingstore.services.user.IUserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/api")
public class AuthenticationController {
    @Autowired
    IUserService iUserService;
    private final AuthenticationManager authenticationManager;

    private final CustomUserDetailsService customUserDetailsService;

    private final JwtTokenUtils jwtTokenUtils;
    private final RestTemplate restTemplate = new RestTemplate();

    public AuthenticationController(AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService,
                                    JwtTokenUtils jwtTokenUtils) {
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtTokenUtils = jwtTokenUtils;
    }

    /***
     * @Authorize: Bất cứ ai cũng có thể đăng ký tài khoản
     * @param signUp : Các thông tin cẩn thiết cho 1 tài khoản mới
     * @return: Thông tin User mới được tạo
     *
     *  User mới sẽ mặc định chỉ có quyền USER
     *  Muốn của quyền ADMIN
     */
    @ApiOperation(value = "Tạo mới user")
    @PostMapping("/signup")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid SignUp signUp) {
        return new ResponseEntity<>(iUserService.createUser(signUp), HttpStatus.CREATED);
    }

    /***
     *
     * @param dto : Dto chứa username và password để đăng nhập
     * @return: Một về 1 Dto chứa token để sử dụng cho các Request sau và 1 số thông tin khác
     */
    @ApiOperation(value = "login form (username, password), avatar null")
    @PostMapping("/login")
    public ResponseEntity<TokenDetails> login(@Valid @RequestBody Login dto) {
        UserDto userDto = iUserService.getUserByUserNameAndPassword(dto.getUsername(), dto.getPassword());
        if (!userDto.getIsActive())
            throw new UserNotFoundAuthenticationException("User has been unactived");
        UserAuthenticationToken authenticationToken = new UserAuthenticationToken(
                dto.getUsername(),
                dto.getPassword(),
                true
        );
        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (UserNotFoundAuthenticationException | BadCredentialsException ex) {
            throw new InvalidException(ex.getMessage());
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        final JwtUserDetails userDetails = customUserDetailsService
                .loadUserByUsername(dto.getUsername());
        final TokenDetails result = jwtTokenUtils.getTokenDetails(userDetails, null);
        log.info(String.format("User %s login at %s", dto.getUsername(), new Date()));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /***
     * @Authorize: ADMIN (Chỉ admin mới có quyền thêm role ADMIN cho User)
     * @param userId: ID của user được thêm Role
     * @return: Thông báo thành công
     */
    @PostMapping("/upgrade/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> upgradeRole(@PathVariable Long  userId) {
        iUserService.upgradeRole(userId);
        return new ResponseEntity<>(String.format("User with ID %s has been upgraded to admin", userId), HttpStatus.OK);
    }

    /***
     *  Dùng để test
     * @param principal
     * @return
     */
    @GetMapping("/hello")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> sayHello(Principal principal) {
        return new ResponseEntity<>(String.format("Hello %s", principal.getName()), HttpStatus.OK);
    }

}
