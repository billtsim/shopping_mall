package com.a88.controller;

import com.a88.Pojo.result;
import com.a88.service.inter.orderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@Slf4j
public class orderController {

    @Autowired
    private orderService OS;

    @GetMapping("/user/{userId}")
    public result getOrderByUserId (@PathVariable int userId) {
        log.info("get order information by userId: {}", userId);
        return result.success(OS.getOrderByuserId(userId));
    }

    @GetMapping("/user/orderNo/{orderNo}")
    public result getOrderByorderNo (@PathVariable String orderNo) {
        log.info("get order information by orderNo: {}", orderNo);
        return result.success(OS.getOrderByorderNo(orderNo));
    }


}
