package com.rosenzest.server.base.util;

import java.util.Date;

import org.springframework.lang.NonNull;

import com.rosenzest.base.JwtPayLoad;
import com.rosenzest.server.base.properties.TokenProperties;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * JwtToken工具类
 * 
 * @author fronttang
 */
public final class JwtTokenUtil {

    @NonNull
    private static TokenProperties tokenProperties;

    private static JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

    private JwtTokenUtil() {

    }

    public static JwtTokenUtil init(TokenProperties tokenProperties) {
        JwtTokenUtil.tokenProperties = tokenProperties;
        return jwtTokenUtil;
    }

    /**
     * 生成token
     */
    public static <T extends JwtPayLoad> String generateToken(T jwtPayLoad) {

        DateTime expirationDate =
            DateUtil.offsetMillisecond(new Date(), Convert.toInt(tokenProperties.getExpire()) * 1000);
        return Jwts.builder().setClaims(BeanUtil.beanToMap(jwtPayLoad)).setSubject(String.valueOf(jwtPayLoad.getUserId()))
            .setIssuedAt(new Date()).setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS512, tokenProperties.getSecret()).compact();
    }

    /**
     * 根据token获取Claims
     */
    private static Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(tokenProperties.getSecret()).parseClaimsJws(token).getBody();
    }

    /**
     * 获取JwtPayLoad部分
     */
    public static <T extends JwtPayLoad> T getJwtPayLoad(String token, Class<T> clazz) {
        Claims claims = getClaimsFromToken(token);
        return BeanUtil.toBean(claims, clazz);
    }

    /**
     * 校验token是否正确
     */
    public static Boolean checkToken(String token) {
        try {
            getClaimsFromToken(token);
            return true;
        } catch (JwtException jwtException) {
            // logger.error("", jwtException);
            return false;
        }
    }

    /**
     * 校验token是否失效
     */
    public static Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            final Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (ExpiredJwtException expiredJwtException) {
            // logger.error("", expiredJwtException);
            return true;
        }
    }

}
