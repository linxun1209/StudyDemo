package org.example.service;

import org.example.model.entiry.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private Map<String, User> userDatabase = new HashMap<>();

    public UserService() {
        // 初始化用户数据
        userDatabase.put("user1", new User("user1", "encrypted_password1", "ROLE_USER", false));
        userDatabase.put("admin", new User("admin", "encrypted_password2", "ROLE_ADMIN", false));
    }

    public User getUserByUsername(String username) {
        return userDatabase.get(username);
    }
}
