package com.xzx.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.xzx.common.constant.JwtConstant.*;


/**
 * Jwt工具类
 * 作者: xzx
 * 创建时间: 2021-03-16-22-14
 **/
public class JwtUtil {
    /**
     * 根据用户名生成token
     *
     * @param username 用户信息
     * @return token
     */
    public static String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, username);
        claims.put(CLAIM_KEY_CREATE, new Date());
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    /**
     * 根据token获取用户名
     *
     * @param token token
     * @return username
     */
    public static String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    /**
     * 判断token是否失效
     *
     * @param token    token
     * @param username 用户名
     * @return 是否有效
     */
    public static boolean validateToken(String token, String username) {
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        String tokenUsername = claims.getSubject();
        Date expire = claims.getExpiration();
        return tokenUsername.equals(username) && !expire.before(new Date());
    }

    /**
     * 刷新token
     *
     * @param token token
     * @return 刷新后的token
     */
    public static String refreshToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        claims.put(CLAIM_KEY_CREATE, new Date());
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }
}
