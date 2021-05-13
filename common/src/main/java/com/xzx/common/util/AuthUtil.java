package com.xzx.common.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 授权信息工具类
 * 作者: xzx
 * 创建时间: 2021-05-12-14-00
 **/
public class AuthUtil {

    public static String getUsername(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return JwtUtil.getUsernameFromToken(token);
    }
}
