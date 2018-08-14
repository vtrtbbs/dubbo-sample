package com.gxyj.test.commons.dao;

import java.util.List;

public class IbatisGenericDao extends DaoSupport {
	
	public static final String POSTFIX_INSERT = ".insert";
	public static final String POSTFIX_UPDATE = ".update";
	public static final String POSTFIX_DELETE_PRIAMARYKEY = ".deleteByPrimaryKey";
	public static final String POSTFIX_SELECT = ".select";
	public static final String POSTFIX_LIST = ".list";
	public static final String POSTFIX_GETALL = ".getAll";
	public static final String POSTFIX_COUNT = ".count";
	
	
	protected final <T, S> T _get(Class<T> entityClass, S id) {
		Object t = getSqlMapClientTemplate().queryForObject(getStatementId(entityClass, ".select"), id);
		return (T)t;
	}

	protected final <T, S> List<T> _list(Class<T> entityClass, List<S> idList) {
		return getSqlMapClientTemplate().queryForList(getStatementId(entityClass, ".list"), idList);
	}

	protected final <T> void _insert(T t) {
		getSqlMapClientTemplate().insert(getStatementId(t.getClass(), ".insert"), t);
	}

	protected final <T> int _update(T t) {
		return getSqlMapClientTemplate().update(getStatementId(t.getClass(), ".update"), t);
	}

	protected final <T, S> int _removeById(Class<T> entityClass, S id) {
		return getSqlMapClientTemplate().delete(getStatementId(entityClass, ".deleteByPrimaryKey"), id);
	}

	protected final <T> Page<T> _pagedQuery(Class<T> entityClass, PageCondition condition) {
		return _pagedQuery(entityClass, condition, ".count", ".getAll");
	}

	protected final <T> Page<T> _pagedQuery(Class<T> entityClass, PageCondition condition, String countSqlId,String pageQuerySqlId) {
		int curPage = condition.getCurPage();
		if (curPage < 1) {
			curPage = 1;
		}
		Integer totalCount = (Integer)_queryForObject(entityClass, countSqlId, condition);
		if ((totalCount == null) || (totalCount.intValue() == 0)) {
			return new Page();
		}
		int totalPageCount = 0;
		int fetchNum = condition.getFetchNum();
		if (fetchNum > 0) {
			totalPageCount = (totalCount.intValue() - 1) / fetchNum + 1;
			if (totalPageCount < curPage) {
				curPage = totalPageCount;
				condition.setCurPage(curPage);
			}
		}
		List list = _queryForList(entityClass, pageQuerySqlId, condition);
		return new Page(condition, totalCount.intValue(), list);
	}

	private final <T> String getStatementId(Class<T> entityClass, String suffix) {
		String shortName = entityClass.getSimpleName();
		if (suffix.charAt(0) != '.') {
			shortName = shortName + ".";
		}
		return shortName + suffix;
	}
	
	
	protected final <U, T, S> List<U> _queryForList(Class<T> entityClass, String statementId, S parameters) {
		return getSqlMapClientTemplate().queryForList(getStatementId(entityClass, statementId), parameters);
	}

	protected final <U, T, S> U _queryForObject(Class<T> entityClass, String statementId, S parameters) {
		return (U) getSqlMapClientTemplate().queryForObject(getStatementId(entityClass, statementId), parameters);
	}

	protected final <S, T> int _update(Class<T> entityClass, String statementId, S parameters) {
		return getSqlMapClientTemplate().update(getStatementId(entityClass, statementId), parameters);
	}

	protected final <T, S> int _delete(Class<T> entityClass, String statementId, S parameters) {
		return getSqlMapClientTemplate().delete(getStatementId(entityClass, statementId), parameters);
	}

	protected final <U, T> List<U> _queryForList(Class<T> entityClass, String statementId) {
		return getSqlMapClientTemplate().queryForList(getStatementId(entityClass, statementId));
	}

	protected final <U, T> U _queryForObject(Class<T> entityClass, String statementId) {
		return (U) getSqlMapClientTemplate().queryForObject(getStatementId(entityClass, statementId));
	}

	protected final <T> int _update(Class<T> entityClass, String statementId) {
		return getSqlMapClientTemplate().update(getStatementId(entityClass, statementId));
	}

	protected final <T> int _delete(Class<T> entityClass, String statementId) {
		return getSqlMapClientTemplate().delete(getStatementId(entityClass, statementId));
	}

	protected final <T, S> void _batch(Class<T> entityClass, String statementId, List<S> list) {
		getSqlMapClientTemplate().batch(getStatementId(entityClass, statementId), list);
	}
	
}
