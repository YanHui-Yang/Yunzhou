package com.yyh.user.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * Author: Tong
 * Date: 2021/4/9
 * Description: 拦截器的配置类
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        //添加jwt的拦截器
        registry.addInterceptor(jwtInterceptor)
                //拦截所有的请求
                .addPathPatterns("/**")
                //放行登录的请求
                .excludePathPatterns("/**/login/**");
    }
}
