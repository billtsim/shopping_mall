package com.a88.config;

import com.a88.interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class webConfig implements WebMvcConfigurer {

    @Autowired
    private LoginCheckInterceptor lci;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 攔截 all 除了login
        registry.addInterceptor(lci).addPathPatterns("/**").excludePathPatterns("/login");
    }
}