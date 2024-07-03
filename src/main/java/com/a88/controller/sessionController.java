package com.a88.controller;

import com.a88.Pojo.result;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
// cookie 技術基本已經不用了
public class sessionController {

    // set cookie
    @GetMapping("/c1")
    public result cookie1(HttpServletResponse response) {
        response.addCookie(new Cookie("login_username", "a88"));
        return result.success();
    }

    // get cookie
    @GetMapping("/c2")
    public result cookie2(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies(); // get all Cookies
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("login_username")) {
                System.out.println("login_username: " + cookie.getValue());
            }
        }
        return result.success();
    }
}
