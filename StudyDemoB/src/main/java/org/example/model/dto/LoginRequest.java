package org.example.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginRequest implements Serializable {
    private String username;
    private String password;  // 用户输入的密码（加密前）
}
