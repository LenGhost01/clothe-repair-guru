package com.chenhaozhe.clothe_guru_code.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import io.jsonwebtoken.security.SignatureException;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtTokenUtil {
    private static final String SELECTED_KEY = "U2FsdGVkX191ceXng0Ju4W8mcpNbDFttPVR5mCqYVeA=";
    private final static SecureDigestAlgorithm<SecretKey, SecretKey> ALGORITHM = Jwts.SIG.HS256;
    public static final SecretKey KEY = Keys.hmacShaKeyFor(SELECTED_KEY.getBytes());

    //生成token
    public static String generateToken(Long id) { // 使用jjwt生成token
        return Jwts.builder()
                .subject(String.valueOf(id))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 设定为10小时期间没有登录就算过期
                .signWith(KEY, ALGORITHM)
                .compact();
    }

//    public static Jws<Claims> checkToken(String token) {
//        try {
//            return Jwts.parser().verifyWith(KEY).build().parseSignedClaims(token);
//        } catch (SignatureException e) {
//            // 无效的签名/密钥
//            throw new TokenObsoleteException("自动登录已失效");
//        } catch (Exception e) {
//            // 其他异常处理 目前将其他异常当做失效处理
//            throw new TokenObsoleteException("自动登录已失效");
//        }
//    }
}
