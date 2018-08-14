package com.gxyj.test.commons.dao;

public final class SimpleIbatisEntityDao<T> extends IbatisEntityDao<T> {
	
	public SimpleIbatisEntityDao(Class<T> entityClass) {
		this.entityClass = entityClass;
	}	
}
