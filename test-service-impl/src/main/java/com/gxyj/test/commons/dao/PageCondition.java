package com.gxyj.test.commons.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PageCondition implements Map<String, Object>, Serializable {
	
	/**
	 * 每页默认数据条数
	 */
	private static final int DEFAULT_PAGE_SIZE = 10;
	/**
	 * 当前页
	 */
	private static final String PAGE_CUR = "curPage";
	/**
	 * 起始页
	 */
	private static final String ROW_FROM = "fromRow";
	/**
	 * 偏移页
	 */	
	private static final String NUM_FETCH = "fetchNum";
	private int curPage = 1;
	private int fetchNum = 10;

	private HashMap<String, Object> map = new HashMap<>(0);

	public HashMap getMap() {
		return this.map;
	}

	public PageCondition(int curPage, int fetchNum) {
		this.curPage = curPage;
		this.fetchNum = fetchNum;
		init();
	}

	private void init() {
		this.map.put("curPage", Integer.valueOf(this.curPage));
		this.map.put("fetchNum", Integer.valueOf(this.fetchNum));
		this.map.put("fromRow", Integer.valueOf((this.curPage - 1) * this.fetchNum));
	}

	public PageCondition() {
		init();
	}

	public int getFromRow() {
		int fromRow = (this.curPage - 1) * this.fetchNum;
		return fromRow;
	}

	public int getCurPage() {
		return this.curPage;
	}

	public int getFetchNum() {
		return this.fetchNum;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
		init();
	}

	public void setFetchNum(int fetchNum) {
		this.fetchNum = fetchNum;
		init();
	}

	public void clear() {
		this.map.clear();
	}

	public boolean containsKey(Object key) {
		return this.map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return this.map.containsValue(value);
	}

	public Set<Map.Entry<String, Object>> entrySet() {
		return this.map.entrySet();
	}

	public Object get(Object key) {
		Object obj = this.map.get(key);

		return obj;
	}

	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	public Set<String> keySet() {
		return this.map.keySet();
	}

	public void putAll(Map<? extends String, ? extends Object> t) {
		this.map.putAll(t);
	}

	public Object remove(Object key) {
		return this.map.remove(key);
	}

	public int size() {
		return this.map.size();
	}

	public Collection<Object> values() {
		return this.map.values();
	}

	public Object put(String key, Object value) {
		if (key.equals("curPage")) {
			String[] aaa = (String[]) value;

			setCurPage(Integer.parseInt(aaa[0]));

			return this.map.put(key, Integer.valueOf(this.curPage));
		}
		if (key.equals("fetchNum")) {
			String[] bbb = (String[]) value;

			setFetchNum(Integer.parseInt(bbb[0]));

			return this.map.put(key, Integer.valueOf(this.fetchNum));
		}

		if (value instanceof String[]) {
			return this.map.put(key, ((String[]) value)[0]);
		}

		return this.map.put(key, value);
	}

}
