package com.a88.controller;

import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AsyncController {

    @GetMapping("/async")
    public DeferredResult<String> handleAsync() {
        DeferredResult<String> result = new DeferredResult<>(30000L); // 超时时间30秒

        // 模拟异步处理
        new Thread(() -> {
            try {
                Thread.sleep(1000); // 模拟延迟
                result.setResult("Success");
            } catch (InterruptedException e) {
                result.setErrorResult("Error");
            }
        }).start();

        return result;
    }
}
