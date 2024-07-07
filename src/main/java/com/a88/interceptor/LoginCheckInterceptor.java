package com.a88.interceptor;

import com.a88.Pojo.result;
import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.a88.utils.Jwtutil;

@Component
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override // true = 放行, false = 不放行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        // 1. get request url
        String url = request.getRequestURL().toString();
        log.info("url: {}", url);

        //2. judge
        if (url.contains("login")) {
            log.info("login function, let it go");
            return true; // 放行
        } else if(url.contains("product")) {
            log.info("products add or delete or change or check, let it go");
            return true; // 放行
        } else if (url.contains("signup")) {
            log.info("application for sign up a new account with user role, let it go");
            return true; // 放行
        } else if (url.contains("reset-password")) {
            log.info("application for reset password, let it go");
            return true; //放行
        } else if(url.contains("get-user")) {
            log.info("application for get user info");
            return true; //放行
        } else if (url.contains("verify-email-change")) {
            log.info("application for change email");
            return true; //放行
        } else if (url.contains("cart")) {
            log.info("application for get cart item");
            return true; //放行
        } else if (url.contains("order")) {
            log.info("application for get order information");
            return true; //放行
        }

        // 3. get JWT
        String jwt = request.getHeader("Authorization");

        //4. judge JWT if no this jwt
        if (!StringUtils.hasLength(jwt)) {
            log.info("token is null, return not_login message");
            result error = result.error("not_login");
            // change to JSON format
            String jsonString = JSONObject.toJSONString(error);
            response.getWriter().write(jsonString);
            return false; // false = 不放行
        }

        // 5. 分析token, if failed , return error
        try {
            Jwtutil.parseJWT(jwt);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("解析token失敗, return fail message");
            result error = result.error("not_login");
            // change to JSON format
            String jsonString = JSONObject.toJSONString(error);
            response.getWriter().write(jsonString);
            return false; // false = 不放行

        }

        // 6. token is 有效, 放行
        log.info("token is effective, 放行");
        return true; // 放行
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
