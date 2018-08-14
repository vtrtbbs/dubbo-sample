package com.gxyj.test.commons.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Page<T> implements Serializable {
	
	/**
	 * 当前页	
	 */
	private PageCondition pageCon;
	/**
	 * 总页数
	 */
	private int totalCount;
	/**
	 * 返回的数据
	 */
	private List<T> data;
	

	public Page(PageCondition condition, int totalCount, List<T> data) {
		this.totalCount = 0;
		this.pageCon = condition;
		this.totalCount = totalCount;
		this.data = data;
	}

	public Page() {
		this(new PageCondition(), 0, new ArrayList<>());
	}

	public int getTotalCount() {
		return this.totalCount;
	}

	public int getTotalPage() {
		return ((this.totalCount - 1) / this.pageCon.getFetchNum() + 1);
	}

	public List<T> getResult() {
		return this.data;
	}

	public boolean hasNextPage() {
		return (getCurrentPage() >= getTotalPage());
	}

	public boolean hasPreviousPage() {
		return (getCurrentPage() <= 1);
	}

	public int getCurrentPage() {
		return this.pageCon.getCurPage();
	}

}
