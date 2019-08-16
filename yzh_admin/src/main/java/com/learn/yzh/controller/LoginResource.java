package com.learn.yzh.controller;

import com.learn.yzh.service.LoginService;
import com.learn.yzh.service.UserService;
import com.learn.yzh.util.BaseController;
import com.learn.yzh.util.Result;
import com.learn.yzh.util.ValidCodeUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping(value = "/index")
@RefreshScope
public class LoginResource extends BaseController {

    private static Logger log = LoggerFactory.getLogger(LoginResource.class);

    @Autowired
    private LoginService loginService;
    @Autowired
    private UserService userService;

    @Value("${version}")
    private String username;

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    @ResponseBody
    public String test(){
        System.out.println(username);
        return username;
    }

    //退出的时候是get请求，主要是用于退出
    @RequestMapping(value = "/toLogin",method = RequestMethod.GET)
    public String login(){
        return "login/index";
    }

    //post登录
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Result<String> login(String username, String password, String verifyCode){

        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                username,
                password);
        //进行验证，这里可以捕获异常，然后返回对应信息
        subject.login(usernamePasswordToken);
        return createSuccessResult("main.do");
    }

    //退出的时候是get请求，主要是用于退出
    @RequestMapping(value = "/main",method = RequestMethod.GET)
    public String main(){
        return "index/miniui";
    }

    @RequestMapping(value = "/websocket",method = RequestMethod.GET)
    public String websocket(){
        return "index/websocket";
    }

    @RequestMapping(value = "/vue",method = RequestMethod.GET)
    public String vue(){
        return "index/vue";
    }

    @RequestMapping(value = "/ckedit",method = RequestMethod.GET)
    public String ckedit(){
        return "index/ckedit";
    }

    @RequestMapping(value = "/kindeditor",method = RequestMethod.GET)
    public String kindeditor(){
        return "index/kindeditor";
    }

    @RequestMapping(value = "/cropper",method = RequestMethod.GET)
    public String cropper(){ return "index/cropper"; }

    @RequestMapping(value = "/cropper2",method = RequestMethod.GET)
    public String cropper2(){
        return "index/cropper2";
    }

    // 登录验证码生成
    @RequestMapping(value = "/getCodeImg", method = RequestMethod.GET)
    public void toLoginimg(HttpServletRequest reqeust,
                           HttpServletResponse response) throws IOException {
        ValidCodeUtil img = ValidCodeUtil.getInstance();
        img.createImage(reqeust, response);
    }

    @RequestMapping(value = "/index")
    public String index(){
        return "index";
    }

    //登出
    @RequestMapping(value = "/logout")
    public String logout(){
        return "logout";
    }

    //错误页面展示
    @RequestMapping(value = "/error",method = RequestMethod.POST)
    public String error(){
        return "error ok!";
    }

    //数据初始化
    @RequestMapping(value = "/addUser")
    public String addUser(@RequestBody Map<String,Object> map){
        return "addUser is ok! \n" + "user";
    }

    //角色初始化
    @RequestMapping(value = "/addRole")
    public String addRole(@RequestBody Map<String,Object> map){
        return "addRole is ok! \n" + "role";
    }

    //注解的使用
    @RequiresRoles("admin")
    @RequiresPermissions("create")
    @RequestMapping(value = "/create")
    public String create(){
        return "Create success!";
    }
}