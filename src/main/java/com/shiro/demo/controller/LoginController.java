package com.shiro.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class LoginController {

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String toLogin(String username,String password){
        String error = null;
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
        } catch (UnknownAccountException|IncorrectCredentialsException e) {
            error = "用户名/密码错误";
            log.error(error,e);
        } catch (AuthenticationException e) {
            //其他错误，比如锁定，如果想单独处理请单独catch处理
            error = "其他错误：" + e.getMessage();
            log.error(error,e);
        }
        if(error != null) {//出错了，返回登录页面
            return "login";
        } else {//登录成功
            return "index";
        }
    }
    @RequestMapping(value = "login",method = RequestMethod.GET)
    public String login(){
        return "login";
    }
    @GetMapping("logout")
    @ResponseBody
    public String logout(){
        SecurityUtils.getSubject().logout();
        return "logoutSuccess";
    }
    @GetMapping("authenticated")
    @ResponseBody
    public String authenticated(){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()) {
            return subject.getPrincipal().toString();
        } else {
            return null;
        }
    }
    @GetMapping("unauthorized")
    @ResponseBody
    public String unauthorized(){
        return "无权限";
    }
    @GetMapping("role")
    @ResponseBody
    public String role(){
        Subject subject = SecurityUtils.getSubject();
        try {

            subject.checkRole("admin");
        }catch (AuthenticationException e){
            log.error(e.getMessage(),e);
            return "无admin角色";
        }
        return "有admin角色";
    }
    @GetMapping("permission")
    @ResponseBody
    public String permission(){
        Subject subject=SecurityUtils.getSubject();
        try {

            subject.checkPermission("user:create");
        }catch (AuthenticationException e){
            log.error(e.getMessage(),e);
            return "无user创建权限";
        }
        return "有权限";
    }
    @GetMapping({"/","/index"})
    public String error(){
        return "index";
    }
    @RequestMapping(name="/formfilterlogin",method = RequestMethod.POST)
    @ResponseBody
    public String formfilterlogin(HttpServletRequest request){
        String errorClassName=(String)request.getAttribute("shiroLoginFailure");
        String error=null;
        if(UnknownAccountException.class.getName().equals(errorClassName)) {
            error= "用户名/密码错误";
        } else if(IncorrectCredentialsException.class.getName().equals(errorClassName)) {
            error="用户名/密码错误";
        } else if(errorClassName != null) {
            error="未知错误：" + errorClassName;
        }
        return error;
    }
    @RequestMapping(name="/formfilterlogin",method = RequestMethod.GET)
    public String login2(){
        return "formfilterlogin";
    }
}
