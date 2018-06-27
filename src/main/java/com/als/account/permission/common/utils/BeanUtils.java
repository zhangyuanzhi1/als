package com.als.account.permission.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BeanUtils implements ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BeanUtils.applicationContext = applicationContext;
    }

    public static<T> T getBean(Class<T> clazz) {
        try {
            return applicationContext.getBean(clazz);
        } catch (Exception e) {
            logger.warn("获取Bean失败", e);
        }
        return null;
    }

    public static<T> T getBean(String name, Class<T> clazz) {
        try {
            return applicationContext.getBean(name, clazz);
        } catch (Exception e) {
            logger.warn("获取Bean失败", e);
        }
        return null;
    }

    public static<T> Map<String, T> getBeans(Class<T> clazz) {
        try {
            return applicationContext.getBeansOfType(clazz);
        } catch (Exception e) {
            logger.warn("获取Beans失败", e);
        }
        return null;
    }
}
