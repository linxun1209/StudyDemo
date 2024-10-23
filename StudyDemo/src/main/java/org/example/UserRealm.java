package org.example;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.example.model.entiry.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Base64;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
        String password = new String(usernamePasswordToken.getPassword());

        // 获取用户信息
        User user = userService.getUserByUsername(username);
        if (user == null || !user.getPassword().equals(encryptPassword(password))) {
            throw new AuthenticationException("用户名或密码错误");
        }

        return new SimpleAuthenticationInfo(username, user.getPassword(), getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 权限控制可在此处实现
        return null;
    }

    private String encryptPassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());  // 替换为实际加密方法
    }
}
