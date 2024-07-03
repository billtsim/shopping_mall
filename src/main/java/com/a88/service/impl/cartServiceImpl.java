package com.a88.service.impl;

import com.a88.Pojo.cart;
import com.a88.Pojo.cartItemDetail;
import com.a88.mapper.cartMapper;
import com.a88.service.inter.cartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class cartServiceImpl implements cartService {

    @Autowired
    private cartMapper CM;


    @Override
    @Transactional
    public List<cartItemDetail> getCartByUserId(Integer userId) {
        if (userId != null) {
            return CM.getCartByUserId(userId);
        } else {
            return null;
        }

    }

    @Override
    @Transactional
    public void removeFromCart(Integer userId, Integer productId) {
        CM.deleteItemFromCart(userId, productId);
    }

    @Override
    @Transactional
    public void addToCart(cart cartItem) {  
        if (cartItem.getUserId() != null && cartItem.getProductId() != null ) {
            cartItem.setCreatingTime(LocalDateTime.now());
            cartItem.setUpdateTime(LocalDateTime.now());
            cartItem.setQuantity(1);

            CM.addToCart(cartItem);
        }
    }

    @Override
    public List<Integer> getCarItemsIdByUserId(Integer userId) {
        if (userId != null) {
            return CM.getCarItemsIdByUserId(userId);
        } else {
            return null;
        }
    }
}
