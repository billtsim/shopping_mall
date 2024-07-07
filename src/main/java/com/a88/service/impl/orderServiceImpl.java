package com.a88.service.impl;

import com.a88.Pojo.orderDetail;
import com.a88.Pojo.products;
import com.a88.mapper.orderMapper;
import com.a88.service.inter.orderService;
import com.a88.service.inter.productService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class orderServiceImpl implements orderService {

    @Autowired
    private orderMapper OM;

    @Autowired
    private productService PS;

    @Override
    public void addOrder(orderDetail OD) {
        OM.addOrder(OD);
    }


    @Override
    public List<orderDetail> getOrderByuserId(Integer userId) {
        if (userId != null) {
            return OM.getOrderByuserId(userId);
        } else {
            return null;
        }
    }

    @Override
    public List<orderDetail> getOrderByorderNo(String orderNo) {
        if (orderNo != null) {
            return OM.getOrderByorderNo(orderNo);
        } else {
            return null;
        }
    }
}
