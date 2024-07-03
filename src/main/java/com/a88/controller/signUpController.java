package com.a88.controller;


import com.a88.Pojo.result;
import com.a88.Pojo.user;
import com.a88.service.inter.userService;
import com.a88.service.impl.verificationServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/signup")
@Slf4j
public class signUpController {

    @Autowired
    private verificationServiceImpl ver;

    @Autowired
    private userService US;

    @PostMapping("/send-code")
    public result sendCode(@RequestParam String email) {
        ver.sendVerificationCode(email);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Verification code sent");
        return result.success(response);
    }

    @PostMapping
    public result signUp(@RequestBody user signUpRequest) {
        log.info("sign up account with usernam:{}, email:{}, password:{}", signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPassword());
        US.save(signUpRequest);
        return result.success("sign up successful");
    }


}
