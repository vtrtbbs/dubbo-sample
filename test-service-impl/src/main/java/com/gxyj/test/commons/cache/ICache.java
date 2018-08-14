package com.gxyj.test.commons.cache;

import java.util.List;
import java.util.Map;

public interface ICache {
	
	public <T, S> T get(S paramS);
	
	public <T, S> T[] list(List<S> paramList);
	
	public <T, S> Map<S, T> map(List<S> paramList);

	public <T, S> void store(T paramT, S paramS);

	public <S> void expire(S paramS);
}
