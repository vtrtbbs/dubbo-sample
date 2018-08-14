package com.gxyj.demo.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gxyj.test.dto.ImageDTO;
import com.gxyj.test.dto.UserDTO;
import com.gxyj.test.service.ImageService;
import com.gxyj.test.service.TestService;
import com.gxyj.test.service.UserService;

@RestController
public class HelloController {
	
	@Autowired TestService testService;
	@Autowired ImageService imageService;
	@Autowired UserService userServices;

	public HelloController() {
	}
	
	@RequestMapping("/hello")
	public String hello(){
		ImageDTO imageDTO = imageService.getImage("000000010965");
		System.out.println(imageDTO.getImgName() + ":" + imageDTO.getImgUrl() + ":" + imageDTO.getProdId());
		return testService.hello("xuly");
	}
	
	@RequestMapping("/user")
	public UserDTO getUserDTO(@RequestParam String userId) {
		UserDTO user = userServices.getUserById(userId);
		return user;
	}

}
