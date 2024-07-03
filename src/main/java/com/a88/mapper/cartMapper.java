package com.a88.mapper;

import com.a88.Pojo.cart;
import com.a88.Pojo.cartItemDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface cartMapper {

    void addToCart(cart cartItem);

    void deleteItemFromCart(Integer userId, Integer productId);

    List<cartItemDetail> getCartByUserId(Integer userId);

    @Select("select productId from db_01.cart where userId=#{userId}")
    List<Integer> getCarItemsIdByUserId(Integer userId);
}
