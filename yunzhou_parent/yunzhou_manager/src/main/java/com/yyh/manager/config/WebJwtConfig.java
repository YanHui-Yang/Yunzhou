package com.yyh.manager.config;

import com.yyh.manager.utils.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author: Tong
 * Date: 2021/4/14
 * Description:
 */
@Configuration
public class WebJwtConfig {

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }

}
