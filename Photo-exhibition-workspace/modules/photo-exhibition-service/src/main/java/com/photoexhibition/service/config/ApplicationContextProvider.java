package com.photoexhibition.service.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextProvider {
	
    private static ApplicationContext context;
    
    static {
    	try {
    		context = new ClassPathXmlApplicationContext("spring-config.xml");
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public static ApplicationContext getContext() {
 
        if (context != null) {
            return context;
        }
        else {
            throw new IllegalStateException("The Spring application context is not yet available.");
        }
    }
   
}	