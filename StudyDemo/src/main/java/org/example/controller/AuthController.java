package org.example.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.example.model.dto.LoginRequest;
import org.example.model.entiry.User;
import org.example.service.UserService;
import org.example.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    // 登录接口
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(loginRequest.getUsername(), loginRequest.getPassword());

        try {
            currentUser.login(token);  // 调用Shiro进行登录
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("用户名或密码错误");
        }

        // 登录成功，生成JWT Token
        String jwtToken = jwtUtil.generateToken(loginRequest.getUsername());
        return ResponseEntity.ok(jwtToken);
    }

    // Token验证接口
    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestParam("token") String token) {
        if (jwtUtil.isTokenExpired(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token已过期");
        }

        String username = jwtUtil.extractUsername(token);
        User user = userService.getUserByUsername(username);

        if (user == null || user.isDisabled()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("用户不存在或已被禁用");
        }

        return ResponseEntity.ok(user);
    }
}