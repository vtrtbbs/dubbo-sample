package com.gxyj.test.commons.factory;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapSession;

public class SqlMapClientTemplate implements ISqlMapClientTemplate {

	private SqlMapClient sqlMapClient_r;
	private SqlMapClient sqlMapClient_w;

	public <T> T insert(String id, T pojo) {
		try {
			this.sqlMapClient_w.insert(id, pojo);
			return pojo;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public <T> int delete(String id, T obj) {
		try {
			return this.sqlMapClient_w.delete(id, obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public <T, U> T queryForObject(String id, U obj) {
		try {
			return (T) this.sqlMapClient_r.queryForObject(id, obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public <T, U> List<T> queryForList(String id, U obj) {
		try {
			return this.sqlMapClient_r.queryForList(id, obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList();
	}

	public <U> int update(String id, U obj) {
		try {
			return this.sqlMapClient_w.update(id, obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void setSqlMapClient_r(SqlMapClient sqlMapClient_r) {
		this.sqlMapClient_r = sqlMapClient_r;
	}

	public void setSqlMapClient_w(SqlMapClient sqlMapClient_w) {
		this.sqlMapClient_w = sqlMapClient_w;
	}

	public void setSqlMapClient(SqlMapClient sqlMapClient) {
		this.sqlMapClient_r = sqlMapClient;
		this.sqlMapClient_w = sqlMapClient;
	}

	public <T> T queryForObject(String id) {
		try {
			return (T) this.sqlMapClient_r.queryForObject(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int update(String id) {
		try {
			return this.sqlMapClient_w.update(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public <T> List<T> queryForList(String id) {
		try {
			return this.sqlMapClient_r.queryForList(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList();
	}

	public int delete(String id) {
		try {
			return this.sqlMapClient_w.delete(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public Object execute(SqlMapClientCallback action) throws Exception {
		SqlMapSession session = this.sqlMapClient_w.openSession();
		try {
			session.startTransaction();
			Object o = action.doInSqlMapClient(session);
			session.commitTransaction();
			return o;
		} finally {
			session.close();
		}
	}

	@Override
	public <S> void batch(String paramString, List<S> paramList) {
		
		
	}

	
}
