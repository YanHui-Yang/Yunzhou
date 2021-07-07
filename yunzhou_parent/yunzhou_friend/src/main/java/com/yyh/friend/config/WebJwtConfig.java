package com.yyh.friend.config;

import com.yyh.friend.utils.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author: Tong
 * Date: 2021/4/11
 * Description: Jwt配置类
 */
@Configuration
public class WebJwtConfig {

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }

}
