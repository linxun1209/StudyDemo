package org.example.model.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;  // 用户输入的密码（加密前）
}
