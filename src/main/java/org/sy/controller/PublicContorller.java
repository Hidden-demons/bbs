package org.sy.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.sy.bean.ResponseResult;
import org.sy.bean.StatusCode;
import org.sy.dto.LoginUser;
import org.sy.service.IUserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.UUID;

/**
 * @Auther: 辉
 * @Date:2019/09/29 14:18
 * @Description: (qwq..)
 * @Version 1.0
 */
@RequestMapping("/pub")
@RestController
public class PublicContorller {
    @Autowired
    private IUserService userService;

    @Resource
    private SessionManager sessionManager;

    @GetMapping("/token")
    @ResponseBody
    //@AvoidDuplicateToken(save = true)
    public Object getToken(HttpServletRequest request){
        //产生一个唯一的字符串 UUID
        String avoidDuplicateTokenStr  = UUID.randomUUID().toString();

        String sessionId = request.getHeader("token");

        Session session = sessionManager.getSession(new DefaultSessionKey(sessionId));
        //放入后台session当中的 防止重复提交的token
        session.setAttribute("avoidDuplicateToken",avoidDuplicateTokenStr);

        //放到响应中的防止重复提交的token
        HashMap<String,Object> map = new HashMap<>();
        map.put("avoidDuplicateToken",avoidDuplicateTokenStr);
        return map;
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseResult login(@RequestBody LoginUser loginUser){
        if (loginUser == null){
            return new ResponseResult(StatusCode.LOGIN_FAILE, false, "请传入用户密码", null);
        }
        //进行一系列判断...
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(loginUser.getUsername(),loginUser.getPassword());


        try {
            subject.login(token);  //统一异常处理
        } catch (Exception e) {
            e.printStackTrace();
        }

        HashMap<String,Object> map = new HashMap<>();

        boolean isAuthenticated = subject.isAuthenticated();

        if(isAuthenticated){
            map.put("sessionId", subject.getSession().getId());
            return new ResponseResult(StatusCode.LOGIN_SUCCESS, true, "登录成功", map);
        }
        return new ResponseResult(StatusCode.LOGIN_FAILE,true,"登陆失败",null);
    }

    @GetMapping("/no_permit")
    @ResponseBody
    public ResponseResult noPermit() {
        return new ResponseResult(StatusCode.NO_PERMIT, false, "没有权限", null);
    }

    @GetMapping("/need_login")
    @ResponseBody
    public ResponseResult needLogin(){
        return new ResponseResult(StatusCode.NO_PERMIT,false,"请使用对应的账号登录",null);
    }

}
