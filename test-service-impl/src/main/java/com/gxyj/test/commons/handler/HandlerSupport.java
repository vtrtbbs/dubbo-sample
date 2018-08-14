package com.gxyj.test.commons.handler;

import java.util.List;

import com.gxyj.test.commons.dao.IbatisEntityDao;

public abstract class HandlerSupport<T> {

	protected abstract IbatisEntityDao<T> getEntityDao();

	protected final <U> void wrapList(List<U> list) {
		wrapList(list, false);
	}

	protected final <U> void wrapList(List<U> list, boolean beforeDbOp) {
		if ((list != null) && (list.size() > 0))
			for (Object u : list) {
				wrap(u, beforeDbOp);
			}
	}

	protected final <U> void wrap(U u) {
		wrap(u, false);
	}

	protected final <U> void wrap(U u, boolean beforeDbOp) {
		if (beforeDbOp)
			wrapBeforeDbOp(u);
		else
			wrapAfterDbOp(u);
	}

	protected final <U> void wrapBeforeDbOp(U u) {
		GenericWrapManage.getInstance().wrapBeforeDbOp(u);
	}

	protected final <U> void wrapAfterDbOp(U u) {
		GenericWrapManage.getInstance().wrapAfterDbOp(u);
	}

}
