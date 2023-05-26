package com.example.admin.help;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.util.Date;

public class JwtHelper {
    private static long tokenExpiration = 24*60*60*1000;
    //token签名密钥
    private static String tokenSignKey = "123456";

    //根据userId和username生成token
    public static String createToken(int userId,String username){
        String token = Jwts.builder()
                .setSubject("USER")
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .claim("userId", userId)
                .claim("username", username)
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }
    //根据token字符串得到用户id(userId)
    public static Integer getUserId(String token){
        if (StringUtils.isEmpty(token))
            return null;
        Jws<Claims> claimsJws =
                Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        Integer userId = (Integer) claims.get("userId");
        return userId;
    }
}
