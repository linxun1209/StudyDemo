package com.xingchen;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;

import java.util.Scanner;

public class ShiroTest {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入账号：");
        String username = sc.next();
        System.out.println("请输入密码：");
        String password = sc.next();

        //1.创建安全管理器
        DefaultSecurityManager securityManager=new DefaultSecurityManager();
        //2.创建realm
        IniRealm iniRealm=new IniRealm("classpath:shiro.ini");
        //3.将realm设置给安全管理器
        securityManager.setRealm(iniRealm);
        //4.将Realm设置给SecurityUtil工具
        SecurityUtils.setSecurityManager(securityManager);
        //5.通过SecurityUtil工具类获取subject对象
        Subject subject=SecurityUtils.getSubject();

        //认证流程
        //将认证账号和密码封装到token中
        UsernamePasswordToken token=new UsernamePasswordToken(username,password);
        //通过subject对象调用login方法进行认证
        boolean flag=false;
        try{
            subject.login(token);
            flag=true;
        }catch (IncorrectCredentialsException e){
            flag=false;
        }
        System.out.println(flag?"登录成功":"登录失败");

        //授权
        //判断是否有某个角色
        System.out.println(subject.hasRole("seller"));

        //判断是否有某个权限
        System.out.println(subject.isPermitted("order-del"));
    }
}
