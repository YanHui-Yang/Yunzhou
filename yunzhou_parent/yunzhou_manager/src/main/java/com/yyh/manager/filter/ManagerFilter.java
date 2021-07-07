package com.yyh.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.yyh.manager.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: Tong
 * Date: 2021/4/13
 * Description: zuul网关过滤器
 */
@Component
public class ManagerFilter extends ZuulFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //获取上下文
        RequestContext requestContext = RequestContext.getCurrentContext();

        //获取Resquest
        HttpServletRequest request = requestContext.getRequest();

        //获取认证的头信息
        String header = request.getHeader("Authorization");

        //放行网关的第一次请求的分发服务的请求.该请求的方法为OPTTIONS
        if (request.getMethod().equals("OPTIONS")) {
            return null;
        }

        //放行登录的请求
        if (request.getRequestURI().indexOf("login") > 0) {
            return null;
        }

        if (header != null) {
            if (header.startsWith("Bearer ")) {
                String token = header.substring(7);

                try {
                    //解析token
                    Claims claims = jwtUtil.parseJWT(token);
                    //获取角色
                    String role = (String) claims.get("roles");
                    if ("admin".equals(role)) {
                        //只有是管理员角色的时候,才把请求放行
                        requestContext.addZuulRequestHeader("Authorization", header);
                        return null;
                    }
                } catch (Exception e) {
                    //解析token出现异常, 不放行此请求
                    e.printStackTrace();
                    //终止运行,不放行
                    requestContext.setSendZuulResponse(false);
                }
            }
        }
        //终止请求, 不再继续执行
        requestContext.setSendZuulResponse(false);
        //403代表权限不足
        requestContext.setResponseStatusCode(403);
        requestContext.setResponseBody("权限不足");
        requestContext.getResponse().setContentType("text/html;charset=utf-8");
        return null;
    }
}
