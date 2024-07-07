package com.a88.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class gameLibrary {
    private int id;
    private int userId;
    private int productId;
    private String additionalInformation;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private products productsDetail;
}
