package com.example.tuonlineclothingstore.controllers;

import com.example.tuonlineclothingstore.dtos.Login;
import com.example.tuonlineclothingstore.dtos.SignUp;
import com.example.tuonlineclothingstore.dtos.UserDto;
import com.example.tuonlineclothingstore.exceptions.NotFoundException;
import com.example.tuonlineclothingstore.repositories.UserRepository;
import com.example.tuonlineclothingstore.services.user.IUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/Api/User")
public class UserController{
    @Autowired
    IUserService iUserService;

    @GetMapping("")
    @ApiOperation(value = "Lấy tất cả user")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> listUser = iUserService.getAllUsers();
        if (listUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listUser, HttpStatus.OK);
    }
//    @GetMapping("/pagination")
//    public ResponseEntity<List<UserDto>> getAllUsersPagination() {
//        List<UserDto> listUser = iUserService.getAllPagingUsers();
//        if (listUser.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        return new ResponseEntity<>(listUser, HttpStatus.OK);
//    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Lấy user theo id")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") long id) {
        UserDto User = iUserService.getUserById(id);
        return new ResponseEntity<>(User, HttpStatus.OK);
    }

    @GetMapping("/check/{username}")
    @ApiOperation(value = "Kiểm tra Username đã tồn tại hay chưa")
    public ResponseEntity<Boolean> checkExistByUsername(@PathVariable("username") String username) {
        Boolean check = iUserService.checkExistByUsername(username.trim());
        return new ResponseEntity<>(check, HttpStatus.OK);
    }

    @ApiOperation(value = "Tạo mới user")
    @PostMapping("/signup")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid SignUp signUp) {
        UserDto savedUser = iUserService.createUser(signUp);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

//    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<UserDto> patchUser(@PathVariable("id") Long UserId,
//                                                     @RequestBody Map<Object, Object> UserDto) {
//        UserDto updatedUser = iUserService.patchUser(UserId , UserDto);
//        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
//    }

    @ApiOperation(value = "Cập nhật lại user")
    @PatchMapping(value = "/update/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("userId") Long UserId,
                                                      @RequestBody UserDto UserDto) throws NoSuchFieldException, IllegalAccessException {
        UserDto updatedUser = iUserService.updateUser(UserId , UserDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @ApiOperation(value = "Vô hiệu hóa user")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long UserId){
        iUserService.deleteUser(UserId);
        return new ResponseEntity<>("User successfully deleted !!", HttpStatus.OK);
    }

    @ApiOperation(value = "Đăng nhập")
    @PostMapping(value = "/login")
    public ResponseEntity<UserDto> login(@RequestBody Login login){
        UserDto userDto =iUserService.getUserByUserNameAndPassword(login.getUserName(), login.getPassword());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
