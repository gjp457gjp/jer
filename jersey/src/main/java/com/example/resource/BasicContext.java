package com.example.resource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by Administrator on 2015/10/8.
 */
public class BasicContext implements ApplicationContextAware {
    ApplicationContext context;

    public <T> T getApiBean(Class<T> type){
        String apiName=type.toString();
        apiName=apiName.substring(apiName.lastIndexOf(".")+1);
        String firstChar = apiName.substring(0,1).toLowerCase();
        return context.getBean(firstChar+apiName.substring(1),type);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
