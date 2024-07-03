package com.a88.Pojo;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class products {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Double originalPrice;
    private Double discount; // 折扣百分比
    private String categories;
    private String imageUrl;
    private String tags;
    private LocalDateTime createTime; // 新添加的字段
    private LocalDateTime updateTime;
    private String mainImage;
    private SystemRequirements minRequirements;
    private SystemRequirements recRequirements;
}
