package org.example.service;

import org.example.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    // 模拟用户验证
    public boolean validateUser(String username, String password) {
        // 这里可以加上实际的用户验证逻辑，比如从数据库中查询
        return "testUser".equals(username) && "/tO2GyYIGEk3gICzTmk9Lg==".equals(password);
    }
}
