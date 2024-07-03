package com.a88.controller;

import com.a88.Pojo.cart;
import com.a88.Pojo.result;
import com.a88.service.inter.cartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@Slf4j
public class cartController {

    @Autowired
    private cartService CS;

    @PostMapping("/add")
    public result addToCart(@RequestBody cart cart ) {
        log.info("add product to user own cart :{}", cart);
        CS.addToCart(cart);
        return result.success();
    }

    @GetMapping("/user/{userId}")
    public result getCartsByUserId(@PathVariable int userId) {
        log.info("get cart items from userId: {}", userId);
        return result.success(CS.getCartByUserId(userId)) ;
    }

    @PostMapping("/remove")
    public result removeFromCart(@RequestParam Integer userId,
                                 @RequestParam Integer productId) {
        log.info("remove item from cart-> userId: {}, productId:{}", userId, productId);
        CS.removeFromCart(userId, productId);
        return result.success();
    }

    @GetMapping("/CarItemsId/{userId}")
    public result getCarItemsIdByUserId(@PathVariable int userId) {
        log.info("get cart items id from userId: {}", userId);
        return result.success(CS.getCarItemsIdByUserId(userId)) ;
    }

}
