package org.sy.filter;

import org.springframework.core.annotation.Order;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: H_ui
 * @Date:2019/10/12 8:46
 * @Description: 是用来过滤特殊字符
 */

@WebFilter("/*")
@Order(value = 1)
public class HTMLFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = null;
        HttpServletResponse response = null;

        try {
            request = (HttpServletRequest) req;
            response = (HttpServletResponse) resp;
        } catch (Exception e) {
            throw new ServletException("non-HTTP request or response");
        }

        //增强request的功能
        HtmlHttpServletRequest htmlRequest = new HtmlHttpServletRequest(request);

        chain.doFilter(htmlRequest, response);
    }

    @Override
    public void destroy() {

    }
}

class HtmlHttpServletRequest extends HttpServletRequestWrapper {
    public HtmlHttpServletRequest(HttpServletRequest request) {
        super(request); //增强request功能
    }

    /**
     * 前台比如表单中，有多少 请求参数，那么该方法就会被调用多少次
     */
    @Override
    public String getParameter(String name) {  //username  <input type="button" value="123"/>
        System.out.println("==============HtmlHttpServletRequest============");

        String value = super.getParameter(name);  //request.getParameter(name);

        if (value == null)
            return value;
        value = filter(value);
        return value;
    }

    private String filter(String message) {  //value => <input type="button" value="123"/>
        if (message == null)
            return null;

        char[] content = new char[message.length()];  //<input type="button" value="123"/>

        //将字符串，分解成字符，放入到 字符数组当中
        message.getChars(0, content.length, content, 0);

        StringBuilder result = new StringBuilder(content.length + 50);

        for (int i = 0; i < content.length; i++) {
            switch (content[i]) {
                case '<':
                    result.append("&lt;");
                    break;
                case '>':
                    result.append("&gt;");
                    break;
                case '"':
                    result.append("&quot;");
                    break;
                case '&':
                    result.append("&amp;");
                    break;
                default:
                    result.append(content[i]);
            }
        }
        return result.toString();
    }
}