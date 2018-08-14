package com.gxyj.test.commons.handler;

import com.gxyj.test.commons.dao.GenericsUtils;
import com.gxyj.test.commons.dao.IbatisEntityDao;
import com.gxyj.test.commons.dao.SimpleIbatisEntityDao;
import com.gxyj.test.commons.factory.ISqlMapClientTemplate;

public class SimpleIbatisEntityHandler<T> extends BaseIbatisEntityHandler<T> {

	protected IbatisEntityDao<T> entityDao;

	public SimpleIbatisEntityHandler() {
		this.entityDao = new SimpleIbatisEntityDao(GenericsUtils.getSuperClassGenricType(super.getClass()));
	}

	protected IbatisEntityDao<T> getEntityDao() {
		return this.entityDao;
	}

	public void setSqlMapClientTemplate(ISqlMapClientTemplate sqlMapClientTemplate) {
		getEntityDao().setSqlMapClientTemplate(sqlMapClientTemplate);
	}
}
