package org.example.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.example.model.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Component
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
        // 从 principals 获取当前用户的身份信息
        String username = (String) principals.getPrimaryPrincipal();

        // 根据用户名查询角色和权限
        // 这里假设有一个方法获取用户角色和权限
        List<String> roles = getRolesByUsername(username);
        List<String> permissions = getPermissionsByUsername(username);

        // 创建 SimpleAuthorizationInfo 对象
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        // 添加角色
        if (roles != null) {
            authorizationInfo.addRoles(roles);
        }

        // 添加权限
        if (permissions != null) {
            authorizationInfo.addStringPermissions(permissions);
        }

        return authorizationInfo;
    }

    // 模拟获取用户角色
    private List<String> getRolesByUsername(String username) {
        // 实际实现中应从数据库或其他数据源获取角色
        return Arrays.asList("admin", "user"); // 示例角色
    }

    // 模拟获取用户权限
    private List<String> getPermissionsByUsername(String username) {
        // 实际实现中应从数据库或其他数据源获取权限
        return Arrays.asList("user:create", "user:delete"); // 示例权限
    }

    private String encryptPassword(String password) {
        try {
            // 创建 MD5 消息摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算哈希值
            byte[] md5Hash = md.digest(password.getBytes());

            // 将哈希值进行 Base64 编码
            return Base64.getEncoder().encodeToString(md5Hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error encrypting password", e);
        }
    }
}
