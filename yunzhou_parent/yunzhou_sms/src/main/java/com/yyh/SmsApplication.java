package com.yyh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Author: Tong
 * Date: 2021/4/4
 * Description: 主启动类
 */
@SpringBootApplication
@EnableEurekaClient
public class SmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmsApplication.class);
    }
}
