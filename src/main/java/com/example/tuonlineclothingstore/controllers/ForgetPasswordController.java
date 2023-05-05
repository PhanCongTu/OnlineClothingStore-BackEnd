package com.example.tuonlineclothingstore.controllers;

import com.example.tuonlineclothingstore.Model.Mail;
import com.example.tuonlineclothingstore.dtos.User.UserDto;
import com.example.tuonlineclothingstore.services.Mail.IMailService;
import com.example.tuonlineclothingstore.services.user.IUserService;
import com.google.gson.Gson;
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

    @PostMapping(value = "/forget")
    public ResponseEntity<String> getMailCodeFromUsername(@RequestParam String username) {
        final Gson gson = new Gson();
        UserDto user = iUserService.getUserByUserName(username.trim());
        if (user != null) {
            int code = (int) Math.floor(((Math.random() * 8999) + 1000));
            Mail mail = new Mail();
            mail.setMailFrom("phancongtu25032002@gmail.com");
            mail.setMailTo(user.getEmail());
            mail.setMailSubject("Reset your password - Tu Clothing Store: ");
            mail.setMailContent("Your code is: " + code + "\nPlease enter the code above to reset your Password!!");
            iMailService.sendEmail(mail);
            return new ResponseEntity<String>(gson.toJson(code), HttpStatus.OK);
        }
        return new ResponseEntity<String>(HttpStatus.OK);
    }
    @PostMapping(value = "/reset")
    public ResponseEntity<String> resetPass(@RequestParam String username,
                                            @RequestParam String newPass) {
        final Gson gson = new Gson();
        iUserService.resetPassword(username, newPass);
        return new ResponseEntity<>(gson.toJson("Update Password Successful"), HttpStatus.OK);
    }
}
