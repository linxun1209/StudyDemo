package org.example.controller;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.example.model.dto.LoginRequest;
import org.example.model.dto.LoginResponse;
import org.example.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authB")
public class AuthUserController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // 验证请求数据
        if (loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
            return ResponseEntity.badRequest().body("Username and password are required");
        }

        try {
            // 调用认证服务进行用户验证
            String token = authenticationService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
            return ResponseEntity.ok(new LoginResponse(token));

        } catch (UnknownAccountException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        } catch (IncorrectCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password");
        } catch (LockedAccountException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Account is locked");
        } catch (Exception e) {
            // 处理其他异常
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during login");
        }
    }
}
