package com.gxyj.test.commons.handler;

import java.util.List;

import com.gxyj.test.commons.dao.Page;
import com.gxyj.test.commons.dao.PageCondition;

public abstract class BaseIbatisEntityHandler<T> extends HandlerSupport<T> implements DataHandler<T> {
	
	public <S> T select(S id) {
		Object t = getEntityDao().findByPrimarykey(id);
		wrap(t);
		return (T)t;
	}

	public final void insert(T t) {
		wrap(t, true);
		getEntityDao().insert(t);
	}

	public int update(T t) {
		wrap(t, true);
		return getEntityDao().update(t);
	}

	public <S> int delete(S id) {
		return getEntityDao().deleteByPrimarykey(id);
	}

	public final <U, S> List<U> queryForList(String statementId, S parameters) {
		List list = getEntityDao().queryForList(statementId, parameters);
		wrapList(list);
		return list;
	}

	public final <U, S> U queryForObject(String statementId, S parameters) {
		Object u = getEntityDao().queryForObject(statementId, parameters);
		wrap(u);
		return (U)u;
	}

	public final <S> int update(String statementId, S parameters) {
		return getEntityDao().update(statementId, parameters);
	}

	public final <S> int delete(String statementId, S parameters) {
		return getEntityDao().delete(statementId, parameters);
	}

	public final <U> List<U> queryForList(String statementId) {
		List list = getEntityDao().queryForList(statementId);
		wrapList(list);
		return list;
	}

	public final <U> U queryForObject(String statementId) {
		Object u = getEntityDao().queryForObject(statementId);
		wrap(u);
		return (U)u;
	}

	public final int update(String statementId) {
		return getEntityDao().update(statementId);
	}

	public final int delete(String statementId) {
		return getEntityDao().delete(statementId);
	}

	public final Page<T> pagedQuery(PageCondition condition) {
		Page page = getEntityDao().pagedQuery(condition);
		if ((page.getResult() != null) && (page.getResult().size() > 0)) {
			wrapList(page.getResult());
		}
		return page;
	}

	public final Page<T> pagedQuery(PageCondition condition, String countSqlId, String pageQuerySqlId) {
		Page page = getEntityDao().pagedQuery(condition, countSqlId, pageQuerySqlId);
		if ((page.getResult() != null) && (page.getResult().size() > 0)) {
			wrapList(page.getResult());
		}
		return page;
	}

	public <S> List<T> list(List<S> idList) {
		List list = getEntityDao().list(idList);

		wrapList(list);

		return list;
	}

	public <S> void batch(String statementId, List<S> list) {
		getEntityDao().batch(statementId, list);
	}
}
