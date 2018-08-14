package com.gxyj.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gxyj.test.dto.UserDTO;
import com.gxyj.test.entity.UserMain;
import com.gxyj.test.mapper.UserMainDAO;
import com.gxyj.test.service.UserService;


@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMainDAO userMainDAO;
	
	@Override
	public UserDTO getUserById(String userId) {
		UserDTO userDto = new UserDTO();
		UserMain userMain  = getUserMainById(userId);
		if (userMain!= null) {
			userDto.setUserId(userMain.getLiveFlag());
			userDto.setUserName(userMain.getNickName());
		}
		return userDto;
	}
	
	public UserMain getUserMainById(String userId) {
		UserMain user = userMainDAO.getUserMain(userId);
		return user;
	}

}
