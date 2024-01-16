package com.xhh.smalldemobackend.service.token;

import com.xhh.smalldemocommon.pojo.SecurityUser;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
public class TokenServiceImpl implements TokenService {
    @Override
    public Long getUserId() {
        Long userId = null;
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if (ObjectUtils.isNotEmpty(securityUser)){
            userId = securityUser.getUserId();
        }
        
        return userId;
    }

    @Override
    public String getUserName() {
        String userName = "";
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if (ObjectUtils.isNotEmpty(securityUser)) {
            userName = securityUser.getUsername();
        }
        return userName;
    }

    @Override
    public String getMobile() {
        return null;
    }
}
