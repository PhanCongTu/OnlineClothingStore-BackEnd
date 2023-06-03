package com.example.tuonlineclothingstore.controllers;

import com.example.tuonlineclothingstore.Model.Mail;
import com.example.tuonlineclothingstore.dtos.User.ChangePasswordDto;
import com.example.tuonlineclothingstore.dtos.User.UpdateUserDto;
import com.example.tuonlineclothingstore.dtos.User.UserDto;
import com.example.tuonlineclothingstore.entities.User;
import com.example.tuonlineclothingstore.services.Mail.IMailService;
import com.example.tuonlineclothingstore.services.user.IUserService;
import com.google.gson.Gson;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserController{

    IUserService iUserService;
    public UserController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    /***
     * @Authorize : ADMIN
     * @param searchText : Từ khóa muốn tìm kiếm (Tên user hoặc email)
     * @param page : Số thự tự của trang
     * @param column : truyền tên Field muốn xắp xếp theo
     * @param sortType: sắp xếp theo:
     *                true => tăng dần,
     *                false => giảm dần
     * @param size: Số lượng kết quả trên 1 trang
     * @return : Trả về 1 Page các user theo từ khóa
     *  Nếu không chuyền vào searchText thì mặc địch sẽ tìm tất cả
     *  Nếu không chuyền vào page thì sẽ lấy trang đầu tiên
     */
    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserDto>> getAllUsers(@RequestParam(defaultValue = "") String searchText,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "name") String column,
                                                     @RequestParam(defaultValue = "10") int size,
                                                     @RequestParam(defaultValue = "true") boolean sortType) {
        String sort = (sortType ? "asc" : "desc") ;
        return new ResponseEntity<>(iUserService.filter(searchText, page, size, sort, column), HttpStatus.OK);
    }

    @GetMapping("/me")
    @ApiOperation(value = "Lấy thông tin cá nhân")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<UserDto> getMyInformation(Principal principal) {
        return new ResponseEntity<>(iUserService.getMyInf(principal), HttpStatus.OK);
    }

    /***
     * @Authorize: ADMIN
     * @param userId : ID của user
     * @return : Trả về thông tin của user đó
     */
    @GetMapping("/{userId}")
    @ApiOperation(value = "Lấy user theo id")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> getUserById(@PathVariable long userId) {
        return new ResponseEntity<>(iUserService.getUserById(userId), HttpStatus.OK);
    }

    /***
     * @Authorize: ADMIN, USER
     * @param username: truyền vào username muốn kiểm tra
     * @return :    true: đã tồn tại
     *              false: chưa tồn tại
     */
    @GetMapping("/check/{username}")
//    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @ApiOperation(value = "Kiểm tra Username đã tồn tại hay chưa")
    public ResponseEntity<Boolean> checkExistByUsername(@PathVariable String username) {
        return new ResponseEntity<>(iUserService.checkExistByUsername(username.trim()), HttpStatus.OK);
    }

    /***
     * @Authorize: ADMIN, USER
     * @param UserDto: Các thông tin muốn cập nhật (Không bao gồm username và password)
     * @return: Trả về thông tin mới của user
     */
    @ApiOperation(value = "Cập nhật lại user")
    @PutMapping(value = "/update")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<UserDto> update(@RequestBody UpdateUserDto UserDto,
                                          Principal principal){
        UserDto updatedUser = iUserService.updateUser(principal , UserDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    /***
     * @Authorize: ADMIN, USER (Ai cũng có thể đổi được mật khẩu của chính mình nhưng phải đăng nhập)
     * @param changePasswordDto : Truyền vào password cũ để check và password mới
     * @param principal : Chỉ thay đổi được password của người đang đăng nhập
     * @return : Thông báo thành công
     */
    @ApiOperation(value = "Đổi mật khẩu user")
    @PutMapping(value = "/change-password")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDto changePasswordDto,
                                          Principal principal){
        iUserService.changePassword(changePasswordDto, principal);
        return new ResponseEntity<>("Password updated successfully", HttpStatus.OK);
    }

    /***
     * @Authorize: ADMIN
     * @param userId : ID của user bị thay đổi trạng thái
     * @return : Trả về trạng thái mới của user
     *              true -> false
     *              false -> true
     */
    @ApiOperation(value = "Đổi trạng thái của User")
    @PutMapping(value = "/change-status/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> changeStatusUser(@PathVariable Long userId){
        UserDto userDto = iUserService.getUserById(userId);
        iUserService.changeStatusUser(userId);
        return new ResponseEntity<>(String.format("User đã được thay đổi trạng thái từ %s thành %s",
                userDto.getIsActive(), !userDto.getIsActive()), HttpStatus.OK);
    }

    /***
     * @Authorize: ADMIN
     * @param userId: ID của user bị xóa
     * @return : Thông báo xóa thành công
     *
     *  HẠN CHẾ tối đa việc sử dụng chức năng này
     *  Thay vào đó chỉ cần đổi trạng thái của user bằng false
     *  Vì sẽ làm mất dữ liệu liên quan (Cart, Order)
     *
     */
    @ApiOperation(value = "Xóa User khỏi hệ thống")
    @DeleteMapping(value = "/delete/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> DeleteUser(@PathVariable Long userId){
        iUserService.deleteUser(userId);
        return new ResponseEntity<>(String.format("User có id là %s đã bị xóa", userId), HttpStatus.OK);
    }


}
