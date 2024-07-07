package com.a88.service.inter;

import com.a88.Pojo.cart;
import com.a88.Pojo.cartItemDetail;

import java.util.List;

public interface cartService {


    List<cartItemDetail> getCartByUserId(Integer userId);

    void removeFromCart(Integer userId, Integer productId);

    void addToCart(cart cartItem);

    List<Integer> getCarItemsIdByUserId(Integer userId);
}