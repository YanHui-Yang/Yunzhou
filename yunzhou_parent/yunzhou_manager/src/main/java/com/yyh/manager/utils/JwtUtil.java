package com.yyh.manager.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;

/**
 * Author: Tong
 * Date: 2021/4/8
 * Description: Jwt工具类
 */
@ConfigurationProperties("jwt.config")
public class JwtUtil {

    /**
     * 生成令牌时的加盐
     */
    private String key;

    /**
     * token 的过期时间
     */
    private long ttl;//一个小时

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    /**
     * 生成JWT
     *
     * @param id      用户的id
     * @param subject 用户的名称
     * @param roles   用户的角色
     * @return
     */
    public String createJWT(String id, String subject, String roles) {
        //获取当前时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().setId(id) //生成token设置用户id
                .setSubject(subject) //设置用户名称
                .setIssuedAt(now) //IssueAt代表登录时间
                .signWith(SignatureAlgorithm.HS256, key) //加密方式, 加盐
                .claim("roles", roles); //设置用户角色
        if (ttl > 0) {
            //如果过期时间大于0 ,那么设置过期时间, 单位为毫秒
            builder.setExpiration(new Date(nowMillis + ttl));
        }
        return builder.compact();
    }

    /**
     * 解析JWT
     *
     * @param jwtStr
     * @return
     */
    public Claims parseJWT(String jwtStr) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwtStr)
                .getBody();
    }
}
