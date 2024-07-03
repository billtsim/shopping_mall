package com.a88.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class cartItemDetail {
    private Integer id;
    private Integer userId;
    private Integer productId;
    private Integer quantity;
    private Boolean selected;
    private LocalDateTime creatingTime;
    private LocalDateTime updateTime;
    private products productInfo;
}

