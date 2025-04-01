package com.meeting_smile.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

//生成jwt令牌的工具类
public class JwtUtils {

    private  static String signKey = "meetingSmile";//签名算法的密钥
    private static Long expire = 43200000L;//jwt令牌的有效期

    /**
     * 生成JWT令牌
     * @param claims
     * @return
     */
    public static String generateJwt(Map<String,Object> claims){
        String jwt = Jwts.builder()
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256,signKey)//指定所使用的加密算法为
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .compact();
        return  jwt;
    }

    /**
     * 解析JWT令牌
     * @param jwt   JWT令牌
     * @return  JWT第二部分负载 payload 中存储的内容
     */
    public static Claims parseJWT(String jwt){
        Claims claims = Jwts.parser()
                .setSigningKey(signKey)
                .parseClaimsJws(jwt)
                .getBody();
        return  claims;
    }
}
