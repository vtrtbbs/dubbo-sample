package com.gxyj.test.commons.dao;

import com.ibatis.sqlmap.client.SqlMapClient;

public class CdoAndSmc {

	private CutDbObj cutDbObj;
	private SqlMapClient sqlMapClient;

	public CutDbObj getCutDbObj() {
		return this.cutDbObj;
	}

	public void setCutDbObj(CutDbObj cutDbObj) {
		this.cutDbObj = cutDbObj;
	}

	public SqlMapClient getSqlMapClient() {
		return this.sqlMapClient;
	}

	public void setSqlMapClient(SqlMapClient sqlMapClient) {
		this.sqlMapClient = sqlMapClient;
	}
}
