package org.example.model;

import lombok.Data;

@Data
public class User {
    private String username;
    //加密后的代码
    private String password;
}
