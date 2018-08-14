package com.gxyj.test.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	public HelloController() {
	}
	
	@RequestMapping("/hello")
	public String hello(){
		return "hello";
	}

}
