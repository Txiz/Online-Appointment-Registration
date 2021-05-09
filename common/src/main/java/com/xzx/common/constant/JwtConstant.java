package com.xzx.common.constant;

/**
 * JWT常量
 * 作者: xzx
 * 创建时间: 2021-03-16-22-13
 **/
public interface JwtConstant {
    // JWT存储的请求头
    String TOKEN_HEADER = "Authorization";
    // JWT加密密钥
    String SECRET = "1239adas832jdks83disk1239jail";
    // 过期时间
    long EXPIRATION = 24 * 60 * 60 * 1000;
    // JWT负载中的开头
    String TOKEN_HEAD = "Bearer";
    // 荷载中的用户名
    String CLAIM_KEY_USERNAME = "sub";
    // 荷载中的创建时间
    String CLAIM_KEY_CREATE = "createTime";
}
