package com.gxyj.demo.consumer;

import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * 
 * @author xuliangyong
 *
 */

@SpringBootApplication 
@EnableAutoConfiguration
@PropertySource({"classpath:dubbo/dubbo.properties"}) 
@ImportResource({"classpath:dubbo/spring-consumer.xml"}) 
public class ConsumerApplication {

	private static volatile boolean running = true;  
	private static final Logger log = LoggerFactory.getLogger(ConsumerApplication.class);
	
	public static void main(String[] args) throws UnknownHostException{
		
        SpringApplication.run(ConsumerApplication.class, args);  
        
        log.info("============= APP Start ON SpringBoot Success =============");  
       
        synchronized (ConsumerApplication.class) {  
            while (running) {  
                try {  
                	ConsumerApplication.class.wait();  
                } catch (Throwable e) {  
                }  
            }  
        }  
       
	}

}
