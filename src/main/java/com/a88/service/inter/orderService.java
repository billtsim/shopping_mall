package com.a88.service.inter;

import com.a88.Pojo.orderDetail;

import java.util.List;

public interface orderService {
    void addOrder(orderDetail OD);

    List<orderDetail> getOrderByuserId(Integer userId);

    List<orderDetail> getOrderByorderNo(String orderNo);
}
