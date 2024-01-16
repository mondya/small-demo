package com.xhh.smalldemobackend.service.registry;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class FileParserRegistry implements ApplicationContextAware {
    
    private static ApplicationContext applicationContext;
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        FileParserRegistry.applicationContext = applicationContext;
    }
    
    public static <T> T getBean(String fileType, Class<T> tClass) {
        return applicationContext.getBean(fileType, tClass);
    }
}
