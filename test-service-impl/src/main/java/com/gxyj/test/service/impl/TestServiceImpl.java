package com.gxyj.test.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gxyj.test.service.TestService;


@Service("testService")
public class TestServiceImpl implements TestService {
	
	/**
	 * 【强制】 slf4j
	 */
	private static final Logger log = LoggerFactory.getLogger(TestServiceImpl.class);
	
	

	public TestServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String hello(String name) {
		log.info("hello xuly!!!");		
		return "hello " + name + "!!!";
	}

}
