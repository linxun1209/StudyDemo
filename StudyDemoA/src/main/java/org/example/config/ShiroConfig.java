package org.example.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.example.realm.UserRealm;
import org.example.util.Md5Base64CredentialsMatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;


@Configuration
public class ShiroConfig {

    @Bean
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        // 使用MD5+BASE64加密
        Md5Base64CredentialsMatcher matcher = new Md5Base64CredentialsMatcher();
        userRealm.setCredentialsMatcher(matcher);
        return userRealm;
    }




    @Bean
    public SecurityManager securityManager(UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 配置拦截器
        Map<String, Filter> filters = new HashMap<>();
        filters.put("authc", new org.apache.shiro.web.filter.authc.FormAuthenticationFilter());
        shiroFilterFactoryBean.setFilters(filters);

        // 配置登录 URL
        shiroFilterFactoryBean.setLoginUrl("/login");

        // 配置拦截规则
        Map<String, String> filterChainDefinitionMap = new HashMap<>();
        filterChainDefinitionMap.put("/api/login", "anon");  // 允许匿名访问
        filterChainDefinitionMap.put("/**", "authc");  // 其余 URL 需要认证
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }
}
