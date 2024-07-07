package com.a88.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class orderDetail {
    private int id;
    private String orderNo;
    private int userId;
    private double totalPrice;
    private String receiverName;
    private String receiverMobile;
    private String receiverAddress;
    private String orderStatus;
    private double postage;
    private String paymentType;
    private LocalDateTime deliveryTime;
    private LocalDateTime payTime;
    private LocalDateTime endTime;
    private int productId;
    private int quantity;
    private products productInfo;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
