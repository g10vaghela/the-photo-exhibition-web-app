package com.photoexhibition.service.util;


import org.springframework.context.ApplicationContext;

import com.photoexhibition.service.config.ApplicationContextProvider;

public class BeanServiceUtil {
	static ApplicationContext context = ApplicationContextProvider.getContext();
	
	public static Object getBeanByName(String beanName) {
    	return context.getBean(beanName);
    }
}