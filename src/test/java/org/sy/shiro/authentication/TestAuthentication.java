package org.sy.shiro.authentication;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

import java.util.Arrays;


public class TestAuthentication {

    public static void main(String[] args) {
        //1, 创建SecurityManager工厂，通过ini配置文件创建SecurityManager工厂
        Factory<SecurityManager> factory =
                new IniSecurityManagerFactory("classpath:config/shiro-custom.ini");

        //2，通过工厂对象，创建SecurityManager（使用了工厂设计）
        SecurityManager securityManager = factory.getInstance();

        //3，使用SecurityUtils 工具类 构建securityManager环境
        SecurityUtils.setSecurityManager(securityManager);

        //4，得到主题对象
        Subject subject = SecurityUtils.getSubject();

        //UsernamePasswordToken记录的是用户输入的 用户名 和 密码
        UsernamePasswordToken usernamePasswordToken =
                new UsernamePasswordToken("lisi","lisi");

        subject.login(usernamePasswordToken);//提交用户名和密码

        //用来判断是否为登录状态
        boolean isAuthenticated = false; //ctrl+ait+T

        try {
            //用来判断是否为登录状态
            //一旦登录失败会抛出各种异常
            isAuthenticated = subject.isAuthenticated();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(isAuthenticated);//true

        if (isAuthenticated == true){
            //判断是否拥有角色
            boolean isHasRole = subject.hasRole("role1");
            System.out.println(isHasRole); //true

            boolean[] isHasRoles = subject.hasRoles(Arrays.asList("role1","role3"));
            System.out.println(isHasRoles[0] + "," +isHasRoles[1]);//true

            boolean isHasAllRole = subject.hasAllRoles(Arrays.asList("role1","role2"));
            System.out.println(isHasAllRole); //false

            boolean parmitted = subject.isPermitted("user:save");

            boolean[] permitted2 = subject.isPermitted("user:save","user:remove");
            System.out.println(parmitted);

            System.out.println("1."+subject.isPermitted("user:remove"));

            //通过checkPermission判断权限，如果没有抛出异常
            subject.checkPermission("user:remove");
        }

/*        subject.logout();//退出

        boolean isAuthenticated2 = subject.isAuthenticated();
        System.out.println(isAuthenticated2);*/

    }



    @Test
    public void testSalt(){
        String source = "lisi";
        String salt = "uiwueylm";
        int hashIterations = 2;

        Md5Hash md5Hash = new Md5Hash(source,salt,hashIterations);

        String password_md5 = md5Hash.toString();
        System.out.println(password_md5);



        SimpleHash simpleHash = new SimpleHash("md5",source,salt,hashIterations);
        System.out.println(simpleHash.toString());

    }

}
