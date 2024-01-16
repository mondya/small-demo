package com.xhh.smalldemocommon.utils;

import com.alibaba.fastjson2.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtTokenUtil {
    
    private static final String SECRET_KEY = "#$#)@Qjssetsad*sl;2))";
    
    private static final long ONE_DAY_STAMP = 60 * 60 * 24 * 1000;

    /**
     * 生成token
     * @param claims
     * @return
     */
    public static String generateToken(Map<String, Object> claims){
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .compact();
    }
    
    public static String getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("jwt格式验证失败,message:{},token:{}", e, token);
            throw new RuntimeException("token已过期");
        }
        
        if (ObjectUtils.isNotEmpty(claims)) {
            return JSON.toJSONString(claims);
        } else {
            return null;
        }
    }
    
    /**
     * token过期时间(一天)
     * @return
     */
    public static Date generateExpirationDate(){
        return new Date(System.currentTimeMillis() + ONE_DAY_STAMP);
    }

    public static void main(String[] args) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", 1);
        claims.put("mobile", "13767152962");
        claims.put("userName", "admin");

        String s = generateToken(claims);
        System.out.println(s);
    }
}
