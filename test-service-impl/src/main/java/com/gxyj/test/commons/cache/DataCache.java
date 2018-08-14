package com.gxyj.test.commons.cache;

import java.util.List;
import java.util.Map;

public class DataCache implements ICache {

	private String split = "_";

	private int expiredTime = 0;
	private ServiceCache cs;
	private String type;
	private String cacheName;

	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	public void init() {
		this.cs = ServiceCacheFactory.getInstance().getServiceCache(this.cacheName);
	}

	public void setCs(ServiceCache cs) {
		this.cs = cs;
	}

	public void setExpiredTime(int expiredTime) {
		this.expiredTime = expiredTime;
	}

	public void setType(String type) {
		this.type = type;
	}

	public <S> void expire(S id) {
		this.cs.expire(this.type, id);
	}

	public <T, S> T get(S id) {
		return this.cs.query(this.type, id);
	}

	public <T, S> T[] list(List<S> idList) {
		return this.cs.listArr(this.type, idList);
	}

	public <T, S> Map<S, T> map(List<S> idList) {
		return this.cs.listMap(this.type, idList);
	}

	public <T, S> void store(T t, S id) {
		if (this.expiredTime == 0)
			this.cs.create(this.type, id, t);
		else
			this.cs.create(this.type, id, t, Integer.valueOf(this.expiredTime));
	}

	public void setSplit(String split) {
		this.split = split;
	}
}
