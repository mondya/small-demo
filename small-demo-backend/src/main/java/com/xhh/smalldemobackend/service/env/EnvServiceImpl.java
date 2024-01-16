package com.xhh.smalldemobackend.service.env;

import com.google.common.base.Preconditions;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class EnvServiceImpl implements EnvService{
    
    @Resource
    private Environment environment;
    
    @Override
    public boolean isDev() {
        String[] activeProfiles = environment.getActiveProfiles();
        Preconditions.checkArgument(activeProfiles.length > 0);

        return "dev".equalsIgnoreCase(activeProfiles[0]);
    }
}
