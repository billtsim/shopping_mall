package com.a88.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class user {
    private Integer id;
    private String username;
    private String fullName;
    private String email;
    private String password;
    private String role;
    private String code;
    private LocalDateTime createTime; // 新添加的字段
    private LocalDateTime updateTime;
    private String resetToken;
    private LocalDateTime tokenCreationTime; // 新增字段
}
