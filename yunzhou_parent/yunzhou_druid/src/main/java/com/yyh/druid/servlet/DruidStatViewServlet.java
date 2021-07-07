package com.yyh.druid.servlet;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * Author: Tong
 * Date: 2021/6/10
 * Description:
 */
@WebServlet(urlPatterns = "/druid/*",
        initParams={
                @WebInitParam(name="allow",value="127.0.0.1"),
                @WebInitParam(name="deny",value="192.168.0.0"),
                @WebInitParam(name="loginUsername",value="admin"),
                @WebInitParam(name="loginPassword",value="admin"),
                @WebInitParam(name="resetEnable",value="false")
        })
public class DruidStatViewServlet extends StatViewServlet {
}
