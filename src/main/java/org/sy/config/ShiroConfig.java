package org.sy.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sy.realm.CustomRealmMD52;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Auther: H_ui
 * @Date:2019/10/08 8:34
 * @Description:
 */
@Configuration
public class ShiroConfig {

    @Bean(value = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        System.out.println("==========ShiroFilterFactoryBean==========");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //需要一个securityManager对象，如何得到？？->注入进来，注入安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //拦截路径需要在bean中做一个配置。
        //需要登录的接口，如果访问某个接口，需要登录，则调用此接口（不过不是前后端分离，则跳转页面）
        shiroFilterFactoryBean.setLoginUrl("/pub/login");

        //登录成功后跳转的页面，如果前后端分离，则不需要这个
        shiroFilterFactoryBean.setSuccessUrl("/");

        //没有权限,未授权就会调用此方法，先验证登录->在验证是否有权限。
        shiroFilterFactoryBean.setUnauthorizedUrl("/pub/not_permit");  //403
        // 拦截器拦截路径，此处有坑，如果使用HashMap，则部门路径是无法拦截，时有时无
        //=>HashMap 根据hash算法，再经过某些处理，进行插入，所以应该使用LinkedHashMap

        Map<String,String> map = new LinkedHashMap<>();


        //配置静态资源可以被匿名访问-->
        map.put("/*.html", "anon");
        map.put("/js/**", "anon");
        map.put("/css/**", "anon");
        map.put("/imgs/**", "anon");
        map.put("/static/css/**", "anon");
        map.put("/static/imgs/**", "anon");
        map.put("/static/js/**", "anon");
        map.put("/static/fonts/**", "anon");


        map.put("/logout", "logout"); //退出过滤器
        map.put("/pub/**", "anon"); //匿名可以访问

        //坑二，过滤器链，是顺序执行的，一般/** 要配置到最下面
        map.put("/**","authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        return shiroFilterFactoryBean;
    }

    @Bean(value = "securityManager")
    public SecurityManager  securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //先设置sessionManager,如果不是前后端分离，不需要设置sessionManager
        //会话管理器
        securityManager.setSessionManager(sessionManager());

        securityManager.setCacheManager(cacheManager());
        //再设置customRealm
        //坑3：设置realm（推荐放置在最后，不然某些情况下不会生效）
        securityManager.setRealm(customRealm());

        return securityManager;
    }

    /*自定义realm*/
    @Bean
    public CustomRealmMD52 customRealm(){
        CustomRealmMD52 customRealmMD52 = new CustomRealmMD52();
        customRealmMD52.setCredentialsMatcher(  hashedCredentialsMatch());

        return customRealmMD52;
    }

    /*密码加解密规则*/
    //产生一个凭证器类的对象 ，并且交给Spring IOC 容器，还要给 CutomRealm
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatch(){
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();

        credentialsMatcher.setHashAlgorithmName("MD5");//设置加密格式
        credentialsMatcher.setHashIterations(1);//设置加密
        //storedCredentialsHexEncoded默认是true，此时用的是密码加密用的是Hex编码；false时用Base64编码
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }
    @Bean
    public SessionManager sessionManager() {
        CustomSessionManager customSessionManager = new CustomSessionManager();

        //设置超时时间，默认30分钟，会话超时，方法里面的单位是毫秒
        customSessionManager.setGlobalSessionTimeout(60000);
        return customSessionManager;
    }
    /**
     *   配置 redisManager
     * */
    public RedisManager getReisManager(){
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("47.97.182.12");
        redisManager.setPort(6379);
        redisManager.setPassword("123456");
        return redisManager;
    }

    /**
     * 配置cacheManager 具体的实现类：可以使用很多缓存技术，redis只是其中一种
     * */
    public RedisCacheManager cacheManager(){
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(getReisManager());

        //设置redis的过期时间
        redisCacheManager.setExpire(120);

        return redisCacheManager;
    }


    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @param securityManager
     * @return
     */

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
     }
}