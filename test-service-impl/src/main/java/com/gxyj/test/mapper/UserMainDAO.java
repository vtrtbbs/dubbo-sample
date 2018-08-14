package com.gxyj.test.mapper;

import com.gxyj.test.commons.dao.Page;
import com.gxyj.test.commons.dao.PageCondition;
import com.gxyj.test.entity.UserMain;

public interface UserMainDAO {
	
	UserMain getUserMain(String userId);
	
	Page<UserMain> findUserMain(PageCondition condition);
}
