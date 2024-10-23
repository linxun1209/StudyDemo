package org.example.util;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import java.security.MessageDigest;
import java.util.Base64;

public class Md5Base64CredentialsMatcher implements CredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String inputPassword = (String) token.getCredentials();
        String storedPassword = (String) info.getCredentials();

        // 对输入密码进行 MD5 + Base64 加密
        String encryptedInputPassword = md5Base64(inputPassword);

        // 比较加密后的密码
        return encryptedInputPassword.equals(storedPassword);
    }

    private String md5Base64(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] md5Hash = md.digest(input.getBytes());
            return Base64.getEncoder().encodeToString(md5Hash);
        } catch (Exception e) {
            throw new RuntimeException("Error encoding password", e);
        }
    }
}
