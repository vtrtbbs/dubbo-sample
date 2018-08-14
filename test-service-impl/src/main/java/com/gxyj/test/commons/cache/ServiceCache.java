package com.gxyj.test.commons.cache;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.danga.MemCached.MemCachedClient;

public class ServiceCache {

	private MemCachedClient mcc;
	private String cacheName;

	protected ServiceCache(String cacheName, MemCachedClient mcc) {
		this.mcc = mcc;
		this.cacheName = cacheName;
	}

	public <S> void expire(String type, S key) {
		String cache_key = generKey(type, key);
		this.mcc.delete(cache_key);
	}

	public <T, S> void create(String type, S key, T value) {
		String cache_key = generKey(type, key);
		this.mcc.set(cache_key, value);
	}

	private <S> String generKey(String type, S key) {
		if (type == null) {
			return this.cacheName + "_" + key.toString();
		}
		return this.cacheName + "_" + type + "_" + key.toString();
	}

	public <T, S> void create(String type, S key, T value, Integer expire) {
		String cache_key = generKey(type, key);
		Calendar ca = Calendar.getInstance();
		ca.add(13, expire.intValue());

		this.mcc.set(cache_key, value, ca.getTime());
	}

	public <T, S> T query(String type, S key) {
		String cache_key = generKey(type, key);
		return (T) this.mcc.get(cache_key);
	}

	public <T, S> T[] listArr(String type, List<S> keyList) {
		String[] keys = new String[keyList.size()];
		for (int i = 0; i < keyList.size(); ++i) {
			keys[i] = generKey(type, keyList.get(i));
		}
		return (T[]) this.mcc.getMultiArray(keys);
	}

	public <T, S> Map<S, T> listMap(String type, List<S> keyList) {
		String[] keys = new String[keyList.size()];
		for (int i = 0; i < keyList.size(); ++i) {
			keys[i] = generKey(type, keyList.get(i));
		}

		Map map = this.mcc.getMulti(keys);
		if ((map != null) && (map.size() > 0)) {
			Map ret = new HashMap();
			Iterator localIterator = keyList.iterator();

			while (true) {
				Object key = (Object) localIterator.next();
				Object t = map.get(generKey(type, key));

				if (t != null)
					ret.put(key, t);
				if (!(localIterator.hasNext())) {
					return ret;
				}
			}
		}
		return null;
	}

	public <S> boolean exists(String type, S key) {
		String cache_key = generKey(type, key);

		return this.mcc.keyExists(cache_key);
	}

	public <S> long incr(String type, S key) {
		return incr(type, key, 1L);
	}

	public <S> long incr(String type, S key, long num) {
		return this.mcc.incr(generKey(type, key), num);
	}

	public <S> long decr(String type, S key) {
		return decr(type, key, 1L);
	}

	public <S> long decr(String type, S key, long num) {
		return this.mcc.decr(generKey(type, key), num);
	}

}
