package com.gxyj.test.commons.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.gxyj.test.commons.cache.DataCache;
import com.gxyj.test.commons.handler.DataHandler;

public abstract class DataService<T, A> {

	private DataCache cache;
	private DataHandler<T> handler;
	private boolean existCache = false;

	protected abstract  A convert(T paramT);

	public <S> void expire(S id) {
		if (this.existCache)
			this.cache.expire(id);
	}

	public <S> void expire(List<S> idList) {
		for (Object id : idList) {
			expire(id);
		}
	}

	public <S> A get(S id) {
		Object a = null;
		if (this.existCache) {
			a = this.cache.get(id);
		}
		if ((a == null) && (this.handler != null)) {
			Object t = this.handler.select(id);

			if (t != null) {
				a = convert((T)t);
				if (this.existCache) {
					this.cache.store(a, id);
				}
			}
		}
		return (A)a;
	}

	public <S> List<A> list(List<S> idList) {
		Object t;
		Object a;
		if ((idList == null) || (idList.size() == 0)) {
			return null;
		}

		Map idMap = new HashMap();
		List idList1 = new ArrayList();
		for (Object id : idList) {
			if (idMap.get(id) == null) {
				idMap.put(id, Integer.valueOf(0));
				idList1.add(id);
			}
		}

		idMap.clear();
		idMap = null;

		List ret = new ArrayList();
		Map map = null;
		Map retMap = new HashMap();
		if (this.existCache) {
			map = this.cache.map(idList1);
		}

		List idList2 = new ArrayList();

		if ((map != null) && (map.size() > 0)) {
			for (Object id : idList1) {
				a = map.get(id);

				if (a == null)
					idList2.add(id);
				else {
					retMap.put(id, a);
				}
			}

			map.clear();
			map = null;
		} else {
			idList2.addAll(idList1);
		}

		if ((idList2.size() > 0) && (this.handler != null)) {
			List ll = this.handler.list(idList2);

			/*if ((ll != null) && (ll.size() > 0)) {
				for (a = ll.iterator(); ((Iterator) a).hasNext();) {
					t = (Object) ((Iterator) a).next();
					Object a = convert((T)t);

					retMap.put(getSerialId(t), a);

					if (this.existCache) {
						this.cache.store(a, getSerialId(t));
					}
				}
			}*/

		}

		if ((retMap != null) && (retMap.size() > 0)) {
			for (t = idList1.iterator(); ((Iterator) t).hasNext();) {
				Object s = (Object) ((Iterator) t).next();
				if (retMap.containsKey(s)) {
					ret.add(retMap.get(s));
				}
			}

			retMap.clear();
			retMap = null;
		}

		return ((List<A>) ret);
	}

	protected abstract Object getSerialId(T paramT);

	public void setCache(DataCache cache) {
		this.cache = cache;
		this.existCache = (cache != null);
	}

	public void setHandler(DataHandler<T> handler) {
		this.handler = handler;
	}

}
