package com.a88.mapper;


import com.a88.Pojo.orderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface orderMapper {
    void addOrder(orderDetail OD);

    List<orderDetail> getOrderByuserId(Integer userId);

    List<orderDetail> getOrderByorderNo(String orderNo);
}
