package org.sy.realm;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.sy.bean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 辉
 * @Date:2019/09/24 16:51
 * @Description: (qwq..)
 * @Version 1.0
 */
public class CustomRealmMD5 extends AuthorizingRealm {


    //用于授权，进行权限校验的时候会被调用
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 获取身份信息
        String username = (String) principals.getPrimaryPrincipal();
        // 模拟 根据身份信息从数据库中查询权限数据
        List<String> permissionList = findPermissionsByUsername(username);
        //查到权限数据，返回授权信息，将权限信息封闭为AuthorizationInfo
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addStringPermissions(permissionList);

        return simpleAuthorizationInfo;
    }

    private  List<String> findPermissionsByUsername(String username){
        List<String> list = new ArrayList<String>();
        list.add("user:save");
        list.add("user:remove");

        return list;
    }
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //将AuthenticationToken父类的token 转成 UsernamePasswordToken
        //token代表用户输入的信息
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //从token中获取身份信息
        String username = token.getUsername();
        //模拟从数据库中取出用户
        User user = findUserByUsername(username);

        if (user == null) { //如果为null说明没有该用户
            return null;  //即可=>  throw new UnknownAccountException();
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), ByteSource.Util.bytes(user.getSalt()), this.getName());

        return simpleAuthenticationInfo;
    }

    //模拟查询数据库返回User类对象
    private User findUserByUsername(String username) {
        return new User(
                1,
                "lisi",
                "c705a6d30fced4985fd82842f7684c04",
                "uiwueylm",
                1);
    }
}
