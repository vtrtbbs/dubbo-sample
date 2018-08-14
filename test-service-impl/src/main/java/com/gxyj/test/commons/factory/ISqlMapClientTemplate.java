package com.gxyj.test.commons.factory;

import java.io.Serializable;
import java.util.List;

public interface ISqlMapClientTemplate {
	
	
	public abstract <T> T insert(String paramString, T paramT);

	  public abstract <T> int delete(String paramString, T paramT);

	  public abstract <T, U> T queryForObject(String paramString, U paramU);

	  public abstract <T, U> List<T> queryForList(String paramString, U paramU);

	  public abstract <U> int update(String paramString, U paramU);

	  public abstract <T> T queryForObject(String paramString);

	  public abstract int update(String paramString);

	  public abstract <T> List<T> queryForList(String paramString);

	  public abstract int delete(String paramString);

	  public abstract <S> void batch(String paramString, List<S> paramList);
	

}
