package com.a88.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class employee {
    private Integer id;
    private String username;
    private String fullName;
    private Short gender;
    private LocalDate entryDate;
    private Short job;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private  Integer department;
    private String password;

}
