package org.sy.realm;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.sy.bean.Permission;
import org.sy.bean.Role;
import org.sy.bean.User;
import org.sy.service.IUserService;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 辉
 * @Date:2019/09/24 16:51
 * @Description: (qwq..)
 * @Version 1.0
 */
public class CustomRealmMD5_2 extends AuthorizingRealm {
    @Autowired
    private IUserService userService;

    //用于授权，进行权限校验的时候会被调用
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 获取身份信息
        String username = (String) principals.getPrimaryPrincipal();

        User user = userService.findAllUserInfoByUsername(username);

        List<String> roleStringList = new ArrayList<>();
        List<String> permissionStringList = new ArrayList<>();

        List<Role> roleList = user.getRoleList();
        for(Role role:roleList){
            roleStringList.add(role.getRolename());

            List<Permission> permissionList = role.getPermissionList();

            for(Permission permission:permissionList){
                if(permission!=null && StringUtils.isNotBlank(permission.getPerCode())){
                    System.out.println("====================>"+permission.getPerCode());
                    permissionStringList.add(permission.getPerCode());
                }

            }
        }

    //查到权限数据，返回授权信息，将权限信息封闭为AuthorizationInfo
    SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        simpleAuthorizationInfo.addRoles(roleStringList);
        simpleAuthorizationInfo.addStringPermissions(permissionStringList);

        return simpleAuthorizationInfo;
}

    @Override
    protected boolean isPermitted(org.apache.shiro.authz.Permission permission, AuthorizationInfo info) {
        return super.isPermitted(permission, info);
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //将AuthenticationToken父类的token 转成 UsernamePasswordToken
        //token代表用户输入的信息
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //从token中获取身份信息
        String username = token.getUsername();

        User user = userService.findSimpleUserInfoByUsername(username);

        if (user == null) { //如果为null说明没有该用户
            return null;  //即可=>  throw new UnknownAccountException();
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), ByteSource.Util.bytes(user.getSalt()), this.getName());

        return simpleAuthenticationInfo;
    }
}
