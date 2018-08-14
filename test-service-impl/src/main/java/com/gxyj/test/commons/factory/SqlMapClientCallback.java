package com.gxyj.test.commons.factory;

import java.sql.SQLException;

import com.ibatis.sqlmap.client.SqlMapExecutor;

public interface SqlMapClientCallback {
	public abstract Object doInSqlMapClient(SqlMapExecutor paramSqlMapExecutor)  throws SQLException;
}
