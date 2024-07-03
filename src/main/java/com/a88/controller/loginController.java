package com.a88.controller;

import com.a88.Pojo.result;
import com.a88.Pojo.user;
import com.a88.service.inter.userService;
import com.a88.utils.Jwtutil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class loginController {

    @Autowired
    private userService US;


    @PostMapping("/login")
    public result login(@RequestBody Map<String, String> loginRequest) {
        String usernameOrEmail = loginRequest.get("usernameOrEmail");
        String password = loginRequest.get("password");

        log.info("user: {}, {}", usernameOrEmail, password);
        user e =  US.login(usernameOrEmail, password);

        // login successful, generate JWT, give JWT
        if (e != null) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", e.getId());
            claims.put("Email", e.getEmail());
            claims.put("Username", e.getUsername());

            String jwt = Jwtutil.generateJwt(claims); // jwt 包含了當前登錄的員工信息
            String username = e.getUsername();
            Integer id = e.getId();


            Map<String, Object> tokenAndUsername = new HashMap<>();
            tokenAndUsername.put("token", jwt);
            tokenAndUsername.put("username", username);
            tokenAndUsername.put("id", id);
            return result.success(tokenAndUsername);
        }


        return result.error("username or password is incorrect");
    }

}
