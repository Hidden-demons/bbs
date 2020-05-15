package org.sy.realm;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.sy.bean.User;

/**
 * @Auther: 辉
 * @Date:2019/09/24 16:51
 * @Description: (qwq..)
 * @Version 1.0
 */
public class CustomRealm extends AuthorizingRealm {
    //用于授权，进行权限校验的时候会被调用
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
    //用于认证 => 登录，当调用subject.login()时候，被调用。
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //将AuthenticationToken父类的token 转成 UsernamePasswordToken
        //token代表用户输入的信息
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //从token中获取身份信息
        String username = token.getUsername();
        String password = new String(token.getPassword());
        //复制一行 ctrl + alt + 下
        System.out.println("username => "+username);
        System.out.println("password => "+password);
        //根据用户名从数据库中查询用户
        User user = findUserByUsername(username);
//        String usernameDB = "list";
//        String passwordDB = "list";

        if(user==null){ //如果为null说明没有该用户
            return null;  //即可=>  throw new UnknownAccountException();
        }
        //如果用户存在
        if(user.getLocked()==0){ //如果为0说明账户被锁定
            throw new LockedAccountException();
        }
        //将从数据库中查询到的 用户名 与 密码 传入到 AuthenticationInfo 对象中
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(),this.getName());

        return simpleAuthenticationInfo;
    }
    private User findUserByUsername(String username) {
        return new User(1,"lisi","lisi","uiwueylm",1);
    }
}
