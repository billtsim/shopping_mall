//package com.a88.filter;
//
//import com.a88.Pojo.result;
//import com.alibaba.fastjson.JSONObject;
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.util.StringUtils;
//import com.a88.utils.Jwtutil;
//import java.io.IOException;
//
//@Slf4j
//@WebFilter(urlPatterns = "/*")
//public class loginCheckFilter implements Filter {
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest req = (HttpServletRequest)servletRequest;
//        HttpServletResponse resp = (HttpServletResponse)servletResponse;
//
//        // 1. get request url
//        String url = req.getRequestURL().toString();
//        log.info("url: {}", url);
//
//        //2. judge
//        if (url.contains("login")) {
//            log.info("login function, let it go");
//            filterChain.doFilter(servletRequest, servletResponse);
//            return;
//        }
//
//        // 3. get JWT
//        String jwt = req.getHeader("token");
//
//        //4. judge JWT if no thisjwt
//        if (!StringUtils.hasLength(jwt)) {
//            log.info("token is null, return not_login message");
//            result error = result.error("not_login");
//            // change to JSON format
//            String jsonString = JSONObject.toJSONString(error);
//            resp.getWriter().write(jsonString);
//            return;
//        }
//
//        // 5. 分析token, if failed , return error
//        try {
//            Jwtutil.parseJWT(jwt);
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.info("解析token失敗, return fail message");
//            result error = result.error("not_login");
//            // change to JSON format
//            String jsonString = JSONObject.toJSONString(error);
//            resp.getWriter().write(jsonString);
//            return;
//
//        }
//
//        // 6. token is 有效, 放行
//        log.info("token is effective, 放行");
//        filterChain.doFilter(servletRequest, servletResponse);
//
//    }
//}
