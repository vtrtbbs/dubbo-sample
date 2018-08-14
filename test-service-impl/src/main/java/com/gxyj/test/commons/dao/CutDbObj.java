package com.gxyj.test.commons.dao;

public abstract class CutDbObj {

	private String tableName;

	public abstract Object getCutDbObjId();

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
