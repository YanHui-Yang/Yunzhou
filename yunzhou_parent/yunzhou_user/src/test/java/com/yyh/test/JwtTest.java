package com.yyh.test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: Tong
 * Date: 2021/4/8
 * Description: Jwt测试
 */
public class JwtTest {
    @Test
    public void testJwt() {
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId("666")  //登录用户的id
                .setSubject("yyh")  //登录用户的名称
                .setIssuedAt(new Date()) //用户登录的时间
                .signWith(SignatureAlgorithm.HS256, "yunzhou"); //头部信息 第一个参数为加密方式为哈希 256  第二个参数为加的盐为yunzhou
        System.out.println(jwtBuilder.compact());
    }

    @Test
    public void testParseJwt() {
        Claims claims = Jwts.parser().setSigningKey("yunzhou") //指定
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NjYiLCJzdWIiOiJ5eWgiLCJpYXQiOjE2MTc4NDUwMzF9.11myZ3xU_E7AwboWQHChXxMaPq1clGzkAlgMfVaju9M")
                .getBody();

        System.out.println("用户的id: " + claims.getId());
        System.out.println("用户名: " + claims.getSubject());
        System.out.println("登录时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getIssuedAt()));
    }
}
