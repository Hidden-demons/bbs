package org.sy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.sy.interceptor.AvoidDuplicateTokenInterceptor;
import org.sy.interceptor.FirstInterceptor;

/**
 * @Auther: H_ui
 * @Date:2019/10/15 8:42
 * @Description:
 */
@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    @Bean(value ="avoidDuplicateTokenInterceptor" )
    public AvoidDuplicateTokenInterceptor avoidDuplicateTokenInterceptor(){
        return new AvoidDuplicateTokenInterceptor();
    }

    @Bean(value = "firstInterceptor")
    public FirstInterceptor firstInterceptor(){
        return new FirstInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(firstInterceptor()).addPathPatterns("/user/**")
                .excludePathPatterns("/user/remove");

        registry.addInterceptor(avoidDuplicateTokenInterceptor()).addPathPatterns("/user/**")
                .excludePathPatterns("/user/remove");
    }

}
