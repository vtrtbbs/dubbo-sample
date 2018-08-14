package com.gxyj.test.commons.cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import com.gxyj.test.commons.service.ServiceConfigure;

public class ServiceCacheFactory {
	
	private static Logger log = Logger.getLogger(ServiceCacheFactory.class.getName());
	
	private static ServiceCacheFactory instance = new ServiceCacheFactory();	

	private Map<String, ServiceCache> instanceMap = Collections.synchronizedMap(new HashMap());

	public static ServiceCacheFactory getInstance() {
		return instance;
	}

	public ServiceCache getServiceCache(String cacheName) {
		ServiceCache cs = (ServiceCache)instanceMap.get(cacheName);
		if (cs == null) {
			synchronized (this) {
				if (cs == null) {
					MemCachedClient mcc = startupService(cacheName);
					if (mcc != null) {
						cs = new ServiceCache(cacheName, mcc);
						this.instanceMap.put(cacheName, cs);
					}
				}
			}
		}
		return cs;
	}

	private MemCachedClient startupService(String cacheName) {
		log.debug("startupService(" + cacheName + ")");

		String str = ServiceConfigure.getInstance().getPropertyAsString(cacheName + "_cache_num", "");

		if (str.equals("")) {
			log.error("can not find " + cacheName + "_cache_num in configure file");
			return null;
		}

		int server_number = Integer.parseInt(str);
		log.debug("Server of " + cacheName + " number = " + server_number);

		String[] servers = new String[server_number];

		for (int i = 1; i <= server_number; ++i) {
			String key = cacheName + "_cache_" + Integer.toString(i);
			str = ServiceConfigure.getInstance().getPropertyAsString(key, "");
			log.debug("get prop(" + key + ") = " + str);
			if (str.equals("")) {
				log.error("can not find " + key);
				return null;
			}
			servers[(i - 1)] = str;
			log.debug("server " + key + " ip and port = " + servers[(i - 1)]);
		}

		return configureServiceCache(cacheName, servers);
	}

	private MemCachedClient configureServiceCache(String cacheName, String[] servers) {
		SockIOPool pool = SockIOPool.getInstance(cacheName);
		pool.setServers(servers);

		pool.setInitConn(ServiceConfigure.getInstance().getPropertyAsInteger("start_connection", 10).intValue());
		pool.setMinConn(ServiceConfigure.getInstance().getPropertyAsInteger("min_connection", 10).intValue());
		pool.setMaxConn(ServiceConfigure.getInstance().getPropertyAsInteger("max_connection", 30).intValue());
		pool.setMaxIdle(ServiceConfigure.getInstance().getPropertyAsInteger("idle_timeout", 600000).intValue());

		pool.setMaintSleep(30L);

		pool.setNagle(false);
		pool.setSocketTO(ServiceConfigure.getInstance().getPropertyAsInteger("read_timeout", 1000).intValue());
		pool.setSocketConnectTO(0);

		pool.initialize();

		MemCachedClient mcc = new MemCachedClient();
		mcc.setPoolName(cacheName);

		mcc.setCompressEnable(true);
		mcc.setCompressThreshold(65536L);
		return mcc;
	}

}
