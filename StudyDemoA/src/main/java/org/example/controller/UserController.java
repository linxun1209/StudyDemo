package org.example.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.example.model.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        // 获取当前用户
        Subject currentUser = SecurityUtils.getSubject();

        // 创建登录令牌
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());

        try {
            // 执行登录
            currentUser.login(token);
            return "Login successful for user: " + user.getUsername();
        } catch (AuthenticationException e) {
            // 登录失败处理
            return "Login failed: " + e.getMessage();
        }
    }
}
