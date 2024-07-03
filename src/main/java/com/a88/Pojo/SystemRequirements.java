package com.a88.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SystemRequirements {
    private String os;
    private String processor;
    private String memory;
    private String graphics;
    private String directX;
    private String network;
    private String storage;
}
