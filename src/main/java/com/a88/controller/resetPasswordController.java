package com.a88.controller;

import com.a88.Pojo.result;
import com.a88.Pojo.user;
import com.a88.service.inter.userService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/reset-password")
@Slf4j
public class resetPasswordController {

    @Autowired
    private userService userService;

    @PostMapping("/forgot")
    public result forgotPassword(@RequestBody Map<String, String> emailRequest) {
        String email = emailRequest.get("email");
        boolean isEmailSent = userService.sendPasswordResetEmail(email);
        if (isEmailSent) {
            return result.success("Reset password email sent successfully");
        } else {
            return result.error("Account does not exist");
        }
    }

    @GetMapping
    public result getUserInfo(@RequestParam("token") String token) {
        user user = userService.getByResetToken(token);
        if (user != null && userService.isTokenValid(user.getTokenCreationTime())) {
            return result.success(user);
        } else {
            return result.error("Invalid or expired token");
        }
    }

    @PutMapping
    public result resetPassword(@RequestBody Map<String, String> passwordResetRequest) {
        String token = passwordResetRequest.get("token");
        String newPassword = passwordResetRequest.get("newPassword");
        boolean isReset = userService.resetPassword(token, newPassword);
        if (isReset) {
            return result.success("Password reset successfully");
        } else {
            return result.error("Failed to reset password");
        }
    }

    @PutMapping("/update-password")
    public result updatePassword(@RequestBody Map<String, String> passwordUpdateRequest) {
        String username = passwordUpdateRequest.get("username");
        String password = passwordUpdateRequest.get("password");
        String newPassword = passwordUpdateRequest.get("newPassword");

        if (userService.updatePassword(username, password, newPassword)) {
            return result.success("Password reset successfully");
        } else {
            return result.error("Failed to reset password");
        }
    }


}