package com.gxyj.test.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import com.gxyj.test.TestApplication;
import com.gxyj.test.commons.dao.Page;
import com.gxyj.test.commons.dao.PageCondition;
import com.gxyj.test.entity.UserMain;
import com.gxyj.test.mapper.UserMainDAO;

/**
 * 单元测试示例
 * @author xuliangyong
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class TestTestService {

	@Autowired
	UserMainDAO userDAO;
	
	@Test
	public void hello(){
		UserMain user = userDAO.getUserMain("343afdsfdsf");
		System.out.println("user:"+user.getAppId());
		
		PageCondition condition = new PageCondition(1,2);
		Page<UserMain> page = userDAO.findUserMain(condition);
		System.out.println("list:"+page.getResult());
		if (!CollectionUtils.isEmpty(page.getResult())) {
			for(UserMain u : page.getResult()) {
				System.out.println("u:"+u.getNickName());
			}
		}
		//ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring/ApplicationContext-simpleprofile.xml");
	    //factory.getBean("DataSource");
	}

}
