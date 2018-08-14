package com.gxyj.test.commons.dao;

public interface EntityDAO<T> {
	
	 public <S> T findByPrimarykey(S paramS);

	 public void insert(T paramT);

	 public int update(T paramT);

	 public <S> int deleteByPrimarykey(S paramS);
}
