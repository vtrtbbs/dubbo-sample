package com.gxyj.test.commons.spring;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.gxyj.test.commons.factory.ContextFactory;

public class ContextFactoryBean<T> implements FactoryBean<T>, InitializingBean {

	private static Logger logger = Logger.getLogger(ContextFactoryBean.class);

	private String contextId;
	private Object obj;

	@Override
	public void afterPropertiesSet() throws Exception {
		if (obj == null) {
			obj = ContextFactory.getInstance().get(contextId);
			logger.debug("contextId : " + contextId);
			if (obj == null) {
				logger.error("obj is null ; contextId : " + contextId);
			}
		}
	}

	@Override
	public T getObject() throws Exception {
		return (T)this.obj;
	}

	@Override
	public Class<?> getObjectType() {
		logger.debug("getObjectType of : " + this.contextId);
	    return this.obj.getClass();
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
	
	public void setContextId(String contextId) {
		this.contextId = contextId;
	}

}
