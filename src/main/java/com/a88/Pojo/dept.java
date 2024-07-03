package com.a88.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class dept {
    private Integer id;
    private String department;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
