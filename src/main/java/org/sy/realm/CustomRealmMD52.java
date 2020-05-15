package org.sy.realm;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
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
public class CustomRealmMD52 extends AuthorizingRealm {
    @Autowired
    private IUserService userService;
    //用于认证，用户登录的时候会被调用
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        //从token中获取用户信息。    token代表用户输入的信息。
        String username = (String)token.getPrincipal();  //token.getUsername()

        User user = userService.findAllUserInfoByUsername(username);

        if(user == null){
            return null;
        }

        if(StringUtils.isBlank(user.getPassword()) ){
            throw new RuntimeException("密码错误");
        }

        SimpleAuthenticationInfo authenticationInfo =
                new SimpleAuthenticationInfo(
                        user,
                        user.getPassword(),
                        ByteSource.Util.bytes(user.getSalt()),
                        this.getName()
                );

        return authenticationInfo;
    }

    //用于授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        String username = (String)principalCollection.getPrimaryPrincipal();
//        User user = userService.findAllUserInfoByUsername(username);

        User newUser = (User) principalCollection.getPrimaryPrincipal();
        User user = userService.findAllUserInfoByUsername(newUser.getUsername());

        List<String> roleStringList = new ArrayList<>();
        List<String> permissionStringList = new ArrayList<>();

        List<Role> roleList = user.getRoleList();

        System.out.println(roleList);

        for (Role role:roleList){
            roleStringList.add(role.getRolename());

            List<Permission> permissionList = role.getPermissionList();
            System.out.println(permissionList);
            if(permissionList!=null){
                for(Permission permission:permissionList){
                    if(permission!=null && StringUtils.isNotBlank(permission.getPerCode())){
                        System.out.println("====================>"+permission.getPerCode());
                        permissionStringList.add(permission.getPerCode());
                    }
                }
            }
        }
    SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(roleStringList);
        simpleAuthorizationInfo.addStringPermissions(permissionStringList);

        return simpleAuthorizationInfo;
    }
    @Override
    protected boolean isPermitted(org.apache.shiro.authz.Permission permission, AuthorizationInfo info) {
        return super.isPermitted(permission, info);
    }
}
