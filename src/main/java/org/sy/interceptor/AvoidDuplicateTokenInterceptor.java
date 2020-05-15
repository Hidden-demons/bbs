package org.sy.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.sy.annotation.AvoidDuplicateToken;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: H_ui
 * @Date:2019/10/15 9:30
 * @Description: 采用继承  extends HandlerInterceptorAdapter 的方式拦截器
 */
public class AvoidDuplicateTokenInterceptor extends HandlerInterceptorAdapter {
    protected final Log logger = LogFactory.getLog(getClass());

    /**
     * @param request
     * @param response
     * @param handler  controller的@Controller注解下的整个类信息
     * @return 返回值：true表示继续流程（如调用下一个拦截器或处理器）；
     * false表示流程中断（如登录检查失败），不会继续调用其他的拦截器或处理器，
     *  此时我们可以通过response来产生响应。
     *@throws Exception
     *
     * **/
    @Resource
    private SessionManager sessionManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {//判断该对象是否是 HandlerMethod 的子类
            //如果没有用泛型，一般咋做强转之前最好HandlerMethod 判断一下
            //表面该对象是否是这个类的子类 或实现了该接口

            HandlerMethod handlerMethod = (HandlerMethod) handler;

            System.out.println("----------->AvoidDuplicateTokenInterceptor ====>" + handlerMethod.getMethod().getName());

            logger.info(handlerMethod.getMethod().getName());
            logger.info(handlerMethod.getBean());

            AvoidDuplicateToken annotation = handlerMethod.getMethod().getAnnotation(AvoidDuplicateToken.class);

            if (annotation != null){
                boolean flag = annotation.rmove();

                if (false){ //如果是true
                    String sessionId = request.getHeader("token") ;
                    Session session = sessionManager.getSession(new DefaultSessionKey(sessionId));

                    String serverToken =(String) session.getAttribute("avoidDuplicateToken");

                    session.removeAttribute("avoidDuplicateToken");
                    if (StringUtils.isBlank(serverToken)){
                        logger.warn("您是重复提交的数据");
                        return false;
                    }

                    String clientToken = request.getHeader("avoidDuplicateToken");
                    logger.info(clientToken);
                    if (StringUtils.isBlank(clientToken)){
                        return false;
                    }

                    if (serverToken.equals(clientToken)){
                        return false;
                    }
                    return true;
                }
            }
            return  false;
        }
        return false;
    }
}
