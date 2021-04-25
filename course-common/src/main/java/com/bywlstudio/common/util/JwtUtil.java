package com.bywlstudio.common.util;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Author: zl
 * @Date: Create in 2021/4/5 18:39
 * @Description:
 */
@Slf4j
public class JwtUtil {
    //常量
    public static final long EXPIRE = 1000 * 60 * 60 * 24; //token过期时间
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO"; //秘钥

    //生成token字符串的方法
    public static String getJwtToken(String username){

        String JwtToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")

                .setSubject("coursePlatform")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))

                .claim("username", username)

                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();

        return JwtToken;
    }

    /**
     * 判断token是否存在与有效
     * @param jwtToken
     * @return
     */
    public static boolean checkToken(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) {
            return false;
        }
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    //解析token
    private static Claims getClaimsFromToken(String token){
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody();
        }catch(ExpiredJwtException e){
            claims= e.getClaims();
        }
        return claims;
    }


    /**
     * 根据token字符串获取用户id
     * @param token
     * @return
     */
    public static String getMemberIdByJwtToken(String token) {
        Claims claims = null ;
        try {
            claims = getClaimsFromToken(token);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return (String)claims.get("username");
    }

    /**
     * 获取当前token 中的过期时间
     * @param token
     * @return
     */

    public static Date getExpiration(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

}
