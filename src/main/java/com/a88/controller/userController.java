package com.a88.controller;

import com.a88.Pojo.products;
import com.a88.Pojo.result;
import com.a88.Pojo.user;
import com.a88.service.inter.userService;
import com.a88.utils.VerificationCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
public class userController {
    @Autowired
    private userService US;

    @GetMapping("/get-user")
    public result getUserInfo(@RequestParam(required = false) Integer id,
                              @RequestParam(required = false) String username,
                              @RequestParam(required = false) String email
                              ) {

        log.info("get user info from database");
        List<user> user = US.getUserinfo(id, username, email);
        return result.success(user);
    }

    @GetMapping("/check-email")
    public result checkEmail(@RequestParam String newEmail) {
        log.info("check is there a duplicated email");
        return result.success(US.checkEmail(newEmail));
    }

    @GetMapping("/verify-code")
    public result verifyCode(@RequestParam String email,
                              @RequestParam String code) {
        log.info("verify code for changing email or may be other thing");
        return result.success(VerificationCodeUtil.validateCode(email, code)) ;
    }

    @GetMapping("/send-verification-link")
    public result sendVerificationLink(@RequestParam String newEmail,
                                        @RequestParam String username) {
        log.info("send email with verificaton link for final verify ");
        return result.success(US.sendVerificationEmail(newEmail, username)) ;
    }
    @Transactional
    @GetMapping("/verify-email-change")
    public ResponseEntity<Void> verifyEmailChange(@RequestParam String token,
                                                  @RequestParam String newEmail) throws UnsupportedEncodingException {
        log.info("reset email address");
        return US.resetEmail(token, newEmail) ;

    }
}

