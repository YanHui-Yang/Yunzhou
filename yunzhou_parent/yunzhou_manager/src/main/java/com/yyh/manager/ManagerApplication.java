package com.yyh.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Author: Tong
 * Date: 2021/4/13
 * Description: zuul服务的启动类
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class ManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }
}
