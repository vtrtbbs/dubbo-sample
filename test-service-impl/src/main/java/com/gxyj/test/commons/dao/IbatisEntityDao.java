package com.gxyj.test.commons.dao;

import java.util.List;

public abstract class IbatisEntityDao<T> extends IbatisGenericDao implements EntityDAO<T> {

	protected Class<T> entityClass;

	public IbatisEntityDao() {
		this.entityClass = GenericsUtils.getSuperClassGenricType(super.getClass());
	}

	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	
	public final <S> T findByPrimarykey(S id) {
		return _get(getEntityClass(), id);
	}

	public final Class<T> getEntityClass() {
		return this.entityClass;
	}

	public final Page<T> pagedQuery(PageCondition condition) {
		return _pagedQuery(getEntityClass(), condition);
	}

	public final Page<T> pagedQuery(PageCondition condition, String countSqlId, String pageQuerySqlId) {
		if ((pageQuerySqlId != null) && (pageQuerySqlId.length() > 0)) {
			return _pagedQuery(getEntityClass(), condition, countSqlId, pageQuerySqlId);
		}
		return _pagedQuery(getEntityClass(), condition);
	}

	public final <S> int deleteByPrimarykey(S id) {
		return _removeById(getEntityClass(), id);
	}

	public final void insert(T o) {
		_insert(o);
	}

	public final int update(T o) {
		return _update(o);
	}

	public final <S> List<T> list(List<S> idList) {
		return _list(getEntityClass(), idList);
	}

	public final <U, S> List<U> queryForList(String statementId, S parameters) {
		return _queryForList(getEntityClass(), statementId, parameters);
	}

	public final <U, S> U queryForObject(String statementId, S parameters) {
		return _queryForObject(getEntityClass(), statementId, parameters);
	}

	public final <S> int update(String statementId, S parameters) {
		return _update(getEntityClass(), statementId, parameters);
	}

	public final <S> int delete(String statementId, S parameters) {
		return _delete(getEntityClass(), statementId, parameters);
	}

	public final <U> List<U> queryForList(String statementId) {
		return _queryForList(getEntityClass(), statementId);
	}

	public final <U> U queryForObject(String statementId) {
		return _queryForObject(getEntityClass(), statementId);
	}

	public final int update(String statementId) {
		return _update(getEntityClass(), statementId);
	}

	public final int delete(String statementId) {
		return _delete(getEntityClass(), statementId);
	}

	public final void batch(String statementId, List list) {
		_batch(getEntityClass(), statementId, list);
	}
	
	

}
