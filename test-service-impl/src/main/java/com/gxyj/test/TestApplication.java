/*
 * Copyright (c) 2015-2017 China CO-OP ELECTRONIC COMMERCE CO. LTD. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * China CO-OP ELECTRONIC COMMERCE CO. LTD. ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of China CO-OP ELECTRONIC COMMERCE CO. LTD.
 */

package com.gxyj.test;

import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;


/**
 * 
 * 
 * @author Danny
 */
@SpringBootApplication 
@EnableAutoConfiguration
@ImportResource({"classpath:spring/*.xml"}) 
@ServletComponentScan
public class TestApplication {
	private static volatile boolean running = true;  
	private static final Logger log = LoggerFactory.getLogger(TestApplication.class);
	
	
	
	public static void main(String[] args) throws UnknownHostException{
		//ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring/ApplicationContext-simpleprofile.xml");
		//ApplicationContext factory = new FileSystemXmlApplicationContext("src/main/resources/spring/ApplicationContext-simpleprofile.xml");
		
        SpringApplication.run(TestApplication.class, args);  
        
        log.info("============= APP Start ON SpringBoot Success =============");  
       
        synchronized (TestApplication.class) {  
            while (running) {  
                try {  
                	TestApplication.class.wait();  
                } catch (Throwable e) {  
                }  
            }  
        }
       
	}

	

}
