package com.gxyj.test.commons.dao;

import com.gxyj.test.commons.factory.ISqlMapClientTemplate;

public abstract class DaoSupport {

	private ISqlMapClientTemplate sqlMapClientTemplate;

	public ISqlMapClientTemplate getSqlMapClientTemplate() {
		return this.sqlMapClientTemplate;
	}	

	public void setSqlMapClientTemplate(ISqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}
}
