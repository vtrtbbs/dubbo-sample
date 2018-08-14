package com.gxyj.test.commons.spring;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import com.gxyj.test.commons.helper.ResourceHelper;
import com.gxyj.test.commons.service.ServiceConfigure;

public class ServiceConfigureInit implements InitializingBean {

	private List<String> fileNames;

	public void setFileNames(List<String> fileNames) {
		this.fileNames = fileNames;
	}

	public void init() {
		try {
			for (String fileName : this.fileNames) {
				ServiceConfigure.getInstance().load(ResourceHelper.getResourceAsStream(fileName));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		init();
	}

}
