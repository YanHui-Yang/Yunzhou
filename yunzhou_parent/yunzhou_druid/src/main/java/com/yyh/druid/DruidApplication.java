package com.yyh.druid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Author: Tong
 * Date: 2021/6/10
 * Description:
 */
@SpringBootApplication
@ServletComponentScan
public class DruidApplication {

    public static void main(String[] args) {
        SpringApplication.run(DruidApplication.class, args);
    }
}
