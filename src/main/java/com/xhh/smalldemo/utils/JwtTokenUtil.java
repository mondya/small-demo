package com.xhh.smalldemo.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Map;

@Slf4j
public class JwtTokenUtil {
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    
    @Value("$jwt.secret")
    private String secret;
    
    private Long expiration;
    
    
    private String generateToken(Map<String, Object> claims){
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    
    private Date generateExpirationDate(){
        return new Date();
    }
}
