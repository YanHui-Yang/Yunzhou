package com.yyh.user.config;

import com.yyh.user.utils.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author: Tong
 * Date: 2021/4/8
 * Description:
 */
@Configuration
public class WebJwtConfig {

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }

}
