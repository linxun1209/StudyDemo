package org.example.service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.example.realm.UserRealm;
import org.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRealm userRealm;

    @Autowired
    private JwtUtil jwtUtil;

    public String authenticate(String username, String password) {
        // 创建 Shiro 认证 token
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(true); // 如果需要支持记住我

        // 获取当前 Subject
        Subject currentUser = SecurityUtils.getSubject();
        
        // 进行身份验证
        currentUser.login(token);

        // 如果认证成功，生成 JWT 或其他类型的 token
        return generateToken(username);
    }

    private String generateToken(String username) {
        // 生成 token 的逻辑
        // 例如，可以使用 JWT 或其他方法
        return jwtUtil.generateToken(username); // 示例
    }
}
