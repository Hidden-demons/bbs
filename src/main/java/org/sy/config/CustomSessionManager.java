package org.sy.config;

import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * @Auther: H_ui
 * @Date:2019/10/08 9:35
 * @Description: 前后端分离的情况下如何做sessionId的控制，如果不是前后端分离，则不需要写
 */
public class CustomSessionManager extends DefaultWebSessionManager {
    private static final  String  AUTHORIZATION= "token";

    public CustomSessionManager() {
        super();
    }
    //alt +insert

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String sessionId = WebUtils.toHttp(request).getHeader(AUTHORIZATION);

        if (sessionId != null) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, ShiroHttpServletRequest.COOKIE_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sessionId);
            //标记sessionid是有效的，如果无效抛异常
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);

            return sessionId;
        }else {
            return super.getSessionId(request,response);
        }
    }
}
