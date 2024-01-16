package com.xhh.smalldemocommon.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {
    
    /*
    密码加密(返回加密后的字符串)
     */
    public static String encodePassword(String pwd){
        return new BCryptPasswordEncoder().encode(pwd);
    }

    /**
     * 验证密码是否正确
     * @param rawPassword 原始密码
     * @param encodePassword 从数据库中查询到的加密后的密码
     * @return boolean
     */
    public static boolean validatePassword(String rawPassword, String encodePassword){
        return new BCryptPasswordEncoder().matches(rawPassword, encodePassword);
    }

    public static void main(String[] args) {
        String pwd = "xhh1990210";
        System.out.println(encodePassword(pwd));
        
        String encodePwd = "$2a$10$0SxNvQeIT01GQnYL1c8NoOx6FPBoE9PR7M4631koqB.i9ZXOYP3BG";

        System.out.println(new BCryptPasswordEncoder().matches(pwd, encodePwd));
    }
}
