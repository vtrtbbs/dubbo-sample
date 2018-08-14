package com.gxyj.test.commons.handler;

import com.gxyj.test.commons.dao.IbatisEntityDao;

public abstract class IbatisEntityHandler <T> extends BaseIbatisEntityHandler<T> {
	
	public abstract void setEntityDao(IbatisEntityDao<T> paramIbatisEntityDao);
}
