//package org.example.service;
//
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.UsernamePasswordToken;
//import org.apache.shiro.subject.Subject;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserServiceImpl {
//    public void checkLogin(String username,String Password) throws Exception{
//        Subject subject= SecurityUtils.getSubject();
//        UsernamePasswordToken token=new UsernamePasswordToken(username,Password);
//        subject.login(token);
//    }
//}
