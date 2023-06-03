package com.example.tuonlineclothingstore.controllers;


import com.example.tuonlineclothingstore.services.Mail.IMailService;
import com.example.tuonlineclothingstore.services.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/password")
@CrossOrigin("*")
public class ForgetPasswordController {
    @Autowired
    IMailService iMailService;
    IUserService iUserService;
    public ForgetPasswordController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    /***
     *
     * @param username: Truyền vào tên tài khoản đã quên mật khẩu để lấy mã gửi về mail
     * @return: Trả về mã đã gửi về mail để check
     */
    @GetMapping(value = "/get-reset-code")
    public ResponseEntity<String> GetResetPasswordCode(@RequestParam String username){
        String resetCode = iUserService.getResetPasswordCode(username);
        return new ResponseEntity<>(resetCode, HttpStatus.OK);
    }

    /***
     *  => Sau khi check mã gửi về mail và mã được trả về giống nhau thì sẽ thực hiện bước này
     * @param username: Tên đăng nhập
     * @param newPassword: Mật khẩu mới
     * @return: True nếu đổi thành công
     *          False nếu thất bại
     */
    @PutMapping(value = "/reset-password")
    public ResponseEntity<Boolean> ResetPassword(@RequestParam String username,
                                                 @RequestParam String newPassword){
        return new ResponseEntity<>(iUserService.resetPassword(username, newPassword), HttpStatus.OK);
    }
}
