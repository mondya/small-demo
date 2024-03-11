package com.xhh.smalldemocommon.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SpringBeanUtil implements ApplicationContextAware {


    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringBeanUtil.applicationContext == null) {
            SpringBeanUtil.applicationContext = applicationContext;
        }
    }

    /**
     * 获取Spring容器中的Bean对象
     *
     * @param clazz 需要获取的Bean的Class类型
     * @param <T>   泛型标记
     * @return 对应类型的Bean实例
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * 根据Bean名称获取Spring容器中的Bean对象
     *
     * @param beanName Bean的名字
     * @param clazz    需要获取的Bean的Class类型
     * @param <T>      泛型标记
     * @return 对应类型的Bean实例
     */
    public static <T> T getBean(String beanName, Class<T> clazz) {
        return applicationContext.getBean(beanName, clazz);
    }
}
