package com.gxyj.test.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gxyj.test.commons.dao.Page;
import com.gxyj.test.commons.dao.PageCondition;
import com.gxyj.test.dao.GenericMybatisDao;
import com.gxyj.test.entity.UserMain;
import com.gxyj.test.mapper.UserMainDAO;

@Repository
public class UserMainDAOImpl extends GenericMybatisDao<UserMain,String> implements UserMainDAO {
	
	public UserMain getUserMain(String userId) {	
		return get(userId);
	}
	
	public Page<UserMain> findUserMain(PageCondition condition) {
		return pagedQuery(condition);
	}
	
}
