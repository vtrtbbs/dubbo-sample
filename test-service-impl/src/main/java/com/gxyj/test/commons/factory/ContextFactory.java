package com.gxyj.test.commons.factory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import com.ibatis.sqlmap.engine.datasource.DataSourceFactory;
import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.gxyj.test.commons.cache.DataCache;
import com.gxyj.test.commons.dao.IbatisEntityDao;
import com.gxyj.test.commons.handler.DataHandler;
import com.gxyj.test.commons.handler.IbatisEntityHandler;
import com.gxyj.test.commons.handler.SimpleIbatisEntityHandler;
import com.gxyj.test.commons.service.DataService;
import com.gxyj.test.commons.service.ServiceConfigure;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.engine.builder.xml.SqlMapConfigParser;
import com.ibatis.sqlmap.engine.config.SqlMapConfiguration;
import com.ibatis.sqlmap.engine.transaction.TransactionConfig;
import com.ibatis.sqlmap.engine.transaction.TransactionManager;

public final class ContextFactory {

	private static ContextFactory instance = new ContextFactory();

	private final Logger log = Logger.getLogger(ContextFactory.class);

	private SqlMapConfiguration sqlMapConfiguration = new SqlMapConfiguration();

	private ServiceConfigure configure = ServiceConfigure.getInstance();

	private Map<String, Object> allMap = new HashMap<>();

	public static ContextFactory getInstance() {
		return instance;
	}

	public void initFile(String xmlFile) throws Exception {
		Document doc = DbHelper.parseXml(xmlFile);
		Element rootElement = doc.getRootElement();

		for (Iterator<?> localIterator = rootElement.elements().iterator(); localIterator.hasNext();) {
			Object o = localIterator.next();
			Element e = (Element) o;
			String name = e.getName();
			if (name != null)
				if (name.equalsIgnoreCase("dataSources"))
					parseElementOfDataSources(e);
				else if (name.equalsIgnoreCase("sqlMapClients"))
					parseElementOfSqlMapClients(e);
				else if (name.equalsIgnoreCase("sqlMapClientFactorys"))
					parseElementOfSqlMapClientFactorys(e);
				else if (name.equalsIgnoreCase("sqlMapClientTemplates"))
					parseElementOfSqlMapClientTemplates(e);
				else if (name.equalsIgnoreCase("daos"))
					parseElementOfDaos(e);
				else if (name.equalsIgnoreCase("handlers"))
					parseElementOfHandlers(e);
				else if (name.equals("caches"))
					parseElementOfCaches(e);
				else if (name.equals("services"))
					parseElementOfServices(e);
				else if (name.equalsIgnoreCase("resource"))
					parseElementOfResource(e);
		}
	}

	private void parseElementOfDataSources(Element datasourcesElement) throws Exception {
		int size = allMap.size();
		if ((datasourcesElement != null) && (datasourcesElement.hasContent())) {
			for (Iterator localIterator = datasourcesElement.elements("dataSource").iterator(); localIterator.hasNext();) {
				Object o = localIterator.next();
				Element datasourceElement = (Element) o;
				DataSource datasource = parseDataSource(datasourceElement);
				allMap.put(datasourceElement.attributeValue("id"), datasource);
			}
		}
		this.log.info("init dataSource end ; dataSource num : " + (allMap.size() - size));
	}

	private DataSource parseDataSource(Element element) throws Exception {
		String type = element.attributeValue("type");
		type = this.sqlMapConfiguration.getTypeHandlerFactory().resolveAlias(type);
		log.info("type : " + type);
		DataSourceFactory dsFactory = (DataSourceFactory) Resources.instantiate(type);

		Properties props = new Properties();
		for (Iterator localIterator = element.elements("property").iterator(); localIterator.hasNext();) {
			Object o = localIterator.next();
			Element ee = (Element) o;
			String val = ee.attributeValue("value");

			if (val.indexOf("$") >= 0) {
				val = this.configure.getPropertyAsString(val.substring(2, val.length() - 1), "");
			}
			String name = ee.attributeValue("name");
			log.info("name : " + name + " ; value : " + val);
			props.setProperty(name, val);
		}
		dsFactory.initialize(props);
		return dsFactory.getDataSource();
	}

	private void parseElementOfSqlMapClientFactorys(Element sqlMapClientFactorysElement) throws Exception {
		int size = this.allMap.size();
		if ((sqlMapClientFactorysElement != null) && (sqlMapClientFactorysElement.hasContent())) {
			for (Iterator localIterator = sqlMapClientFactorysElement.elements("transactionManager").iterator(); localIterator.hasNext();) {
				Object o = localIterator.next();
				Element e = (Element) o;
				SqlMapClientFactory factory = parseClientFactory(e);
				this.allMap.put(e.attributeValue("id"), factory);
			}
		}
		log.info("init sqlMapClientFactory end ; sqlMapClientFactory num : " + (allMap.size() - size));
	}

	private SqlMapClientFactory parseClientFactory(Element element) throws Exception {
		String datasources = element.elementText("dataSources");
		String filterClassName = element.elementText("filter");
		String datasources_w = element.elementText("dataSources_w");
		String datasources_r = element.elementText("dataSources_r");
		String tableNamePre = element.elementText("tableNamePre");

		if ((datasources != null) && (datasources.length() > 0) && (filterClassName != null)
				&& (filterClassName.length() > 0)) {
			String configXml = element.elementText("configLocation");

			String type = element.attributeValue("type");
			boolean commitRequired = "true".equals(element.attributeValue("commitRequired"));
			type = this.sqlMapConfiguration.getTypeHandlerFactory().resolveAlias(type);
			Properties props = new Properties();
			for (Iterator localIterator = element.elements("property").iterator(); localIterator.hasNext();) {
				Object o = localIterator.next();
				Element ee = (Element) o;
				props.setProperty(ee.attributeValue("name"), ee.attributeValue("value"));
			}

			List sqlMapClientList = parseClients(datasources, type, commitRequired, props, configXml);
			List sqlMapClientList_r = parseClients(datasources_r, type, commitRequired, props, configXml);
			List sqlMapClientList_w = parseClients(datasources_w, type, commitRequired, props, configXml);

			CutDbFilter filter = (CutDbFilter) Class.forName(filterClassName).newInstance();
			SqlMapClientFactory factory = new SqlMapClientFactory();
			factory.setFilter(filter);

			if (sqlMapClientList.size() > 0) {
				factory.setSqlMapClientList(sqlMapClientList);
			} else {
				factory.setSqlMapClientList_w(sqlMapClientList_w);
				factory.setSqlMapClientList_r(sqlMapClientList_r);
			}

			if (tableNamePre != null) {
				factory.setTableNamePre(tableNamePre);
			}

			return factory;
		}

		return null;
	}

	private List<SqlMapClient> parseClients(String datasources, String type, boolean commitRequired, Properties props, String configXml) throws Exception {
		    if ((datasources != null) && (datasources.length() > 0)) {
		      String[] arr = datasources.split(",");
		      List sqlMapClientList = new ArrayList();

		      String[] arrayOfString1 = arr; int i = 0; int j = arrayOfString1.length;
		      while (true) { 
		    	  String datasourceId = arrayOfString1[i];
		        SqlMapClient client = parseClient(datasourceId, type, commitRequired, props, configXml);

		        sqlMapClientList.add(client);
		        ++i;
		        if (i >= j)
		        {
		          return sqlMapClientList; 
		        }
		      }
		    }
		    return null;
		  }

	private void parseElementOfSqlMapClients(Element sqlMapClientsElement) throws Exception {
		int size = this.allMap.size();
		if ((sqlMapClientsElement != null) && (sqlMapClientsElement.hasContent())) {
			for (Iterator localIterator = sqlMapClientsElement.elements("transactionManager").iterator(); localIterator.hasNext();) {
				Object o = localIterator.next();
				Element e = (Element) o;
				SqlMapClient client = parseClient(e);
				this.allMap.put(e.attributeValue("id"), client);
			}
		}
		log.info("init sqlMapClient end ; sqlMapClient num : " + (this.allMap.size() - size));
	}

	private SqlMapClient parseClient(Element element) throws Exception {
		String configXml = element.elementText("configLocation");
		Properties props = new Properties();
		for (Iterator localIterator = element.elements("property").iterator(); localIterator.hasNext();) {
			Object o = localIterator.next();
			Element ee = (Element) o;
			props.setProperty(ee.attributeValue("name"), ee.attributeValue("value"));
		}
		String type = element.attributeValue("type");
		boolean commitRequired = "true".equals(element.attributeValue("commitRequired"));
		type = sqlMapConfiguration.getTypeHandlerFactory().resolveAlias(type);
		return parseClient(element.elementText("dataSource"), type, commitRequired, props, configXml);
	}

	private SqlMapClient parseClient(String configXml) throws Exception {
		return new SqlMapConfigParser().parse(Resources.getResourceAsReader(configXml));
	}

	private SqlMapClient parseClient(String datasource, String type, boolean commitRequired, Properties props,String configXml) throws Exception {
		TransactionConfig config = (TransactionConfig) Resources.instantiate(type);
		log.info("config.class : " + config.getClass().getName() + " ; " + commitRequired);
		config.setForceCommit(commitRequired);

		if (!(props.isEmpty())) {
			config.setProperties(props);
		}

		config.setDataSource((DataSource) get(datasource));
		SqlMapClient client = parseClient(configXml);
		applyTransactionConfig(client, config);
		return client;
	}

	private void applyTransactionConfig(SqlMapClient sqlMapClient, TransactionConfig transactionConfig) {
		SqlMapClientImpl extendedClient = (SqlMapClientImpl) sqlMapClient;
		extendedClient.getDelegate().setTxManager(new TransactionManager(transactionConfig));
	}

	private void parseElementOfSqlMapClientTemplates(Element sqlMapClientTemplateElement) throws Exception {
		int size = allMap.size();
		if ((sqlMapClientTemplateElement != null) && (sqlMapClientTemplateElement.hasContent())) {
			for (Iterator localIterator = sqlMapClientTemplateElement.elements("sqlMapClientTemplate").iterator(); localIterator.hasNext();) {
				Object o = localIterator.next();
				Element e = (Element) o;
				String id = e.attributeValue("id");
				String ex = e.attributeValue("ex");
				if ("true".equals(ex)) {
					SqlMapClientTemplateEx template = new SqlMapClientTemplateEx();
					String str_sqlMapClientFactory = e.attributeValue("sqlMapClientFactory");
					template.setSqlMapClientFactory((SqlMapClientFactory) get(str_sqlMapClientFactory));
					allMap.put(id, template);
				} else {
					SqlMapClientTemplate template = new SqlMapClientTemplate();
					String str_SqlMapClient = e.attributeValue("sqlMapClient");
					if (str_SqlMapClient != null) {
						template.setSqlMapClient((SqlMapClient) get(str_SqlMapClient));
					} else {
						template.setSqlMapClient_r((SqlMapClient) get(e.attributeValue("sqlMapClient_r")));
						template.setSqlMapClient_w((SqlMapClient) get(e.attributeValue("sqlMapClient_w")));
					}
					allMap.put(id, template);
				}
			}
		}
		log.info("init sqlMapClientMapTemplate end ; sqlMapClientTemplate num : " + (allMap.size() - size));
	}

	private void parseElementOfDaos(Element daosElement) throws Exception {
		int size = allMap.size();
		if ((daosElement != null) && (daosElement.hasContent())) {
			for (Iterator localIterator = daosElement.elements("dao").iterator(); localIterator.hasNext();) {
				Object o = localIterator.next();
				Element e = (Element) o;

				String id = e.attributeValue("id");
				String clazz = e.attributeValue("class");

				if ((id == null) || (id.equals(""))) {
					id = clazz;
				}

				String str_SqlMapClientTemplate = e.attributeValue("sqlMapClientTemplate");
				Object a = Class.forName(clazz).newInstance();
				IbatisEntityDao dao = (IbatisEntityDao) a;
				dao.setSqlMapClientTemplate((ISqlMapClientTemplate) get(str_SqlMapClientTemplate));
				allMap.put(id, dao);
			}
		}
		log.info("init dao end ; dao num : " + (allMap.size() - size));
	}

	@Autowired
	private void parseElementOfHandlers(Element handlersElement) throws Exception {
		int size = allMap.size();
		if ((handlersElement != null) && (handlersElement.hasContent())) {
			for (Iterator localIterator1 = handlersElement.elements("handler").iterator(); localIterator1.hasNext();) {
				Object o = localIterator1.next();
				Element e = (Element) o;

				String id = e.attributeValue("id");
				String clazz = e.attributeValue("class");
				String dao = e.attributeValue("entityDao");
				String template = e.attributeValue("template");

				Object c = Class.forName(clazz).newInstance();

				log.info("parseHandler : " + id + " ; " + clazz + " ; " + dao + " ; " + template);

				if ((dao != null) && (dao.length() > 0) && (c instanceof IbatisEntityHandler)) {
					IbatisEntityHandler ibatisEntityHandler = (IbatisEntityHandler) c;
					ibatisEntityHandler.setEntityDao((IbatisEntityDao) (IbatisEntityDao) get(dao));
				}

				if ((template != null) && (template.length() > 0) && (c instanceof SimpleIbatisEntityHandler)) {
					SimpleIbatisEntityHandler ibatisEntityHandler = (SimpleIbatisEntityHandler) c;
					ibatisEntityHandler.setSqlMapClientTemplate((ISqlMapClientTemplate) get(template));
				}

				for (Iterator localIterator2 = e.elements("property").iterator(); localIterator2.hasNext();) {
					Object o1 = localIterator2.next();
					Element e1 = (Element) o1;

					String name = e1.attributeValue("name");
					String ref = e1.attributeValue("ref");
					Object value = e1.attributeValue("value");

					if (ref != null) {
						value = get(ref);
					}

					invokeFiled(c, name, value);
				}

				allMap.put(id, c);
			}
		}

		log.info("init handler end ; handler num : " + (this.allMap.size() - size));
	}

	private void invokeFiled(Object o, String filedName, Object value) throws Exception {
		Field f = o.getClass().getDeclaredField(filedName);

		boolean accessible = f.isAccessible();

		if (!(accessible)) {
			f.setAccessible(true);
		}

		f.set(o, value);

		if (!(accessible))
			f.setAccessible(false);
	}

	private void parseElementOfCaches(Element cachesElement) throws Exception {
		int size = allMap.size();

		if ((cachesElement != null) && (cachesElement.hasContent())) {
			for (Iterator localIterator = cachesElement.elements("cache").iterator(); localIterator.hasNext();) {
				Object obj = localIterator.next();
				Element e = (Element) obj;

				String id = e.attributeValue("id");
				String clazz = e.attributeValue("class");
				String type = e.attributeValue("type");
				String cacheName = e.attributeValue("cacheName");
				String s_expiredTime = e.attributeValue("expiredTime");
				int expiredTime = 0;

				if (s_expiredTime != null) {
					expiredTime = Integer.parseInt(s_expiredTime);
				}

				Object c = Class.forName(clazz).newInstance();
				DataCache cache = (DataCache) c;

				cache.setCacheName(cacheName);
				cache.setExpiredTime(expiredTime);
				cache.setType(type);
				cache.init();

				allMap.put(id, cache);
			}
		}

		this.log.info("init cache end ; cache num : " + (this.allMap.size() - size));
	}

	private void parseElementOfServices(Element servicesElement) throws Exception {
		int size = this.allMap.size();

		if ((servicesElement != null) && (servicesElement.hasContent())) {
			for (Iterator localIterator = servicesElement.elements("service").iterator(); localIterator.hasNext();) {
				Object o = localIterator.next();
				Element e = (Element) o;

				String id = e.attributeValue("id");
				String clazz = e.attributeValue("class");
				String handlerName = e.attributeValue("handler");
				String cacheName = e.attributeValue("cache");

				DataHandler handler = (DataHandler) get(handlerName);
				DataCache cache = (DataCache) get(cacheName);

				Object c = Class.forName(clazz).newInstance();

				DataService service = (DataService) c;
				service.setCache(cache);
				service.setHandler(handler);

				allMap.put(id, service);
			}
		}

		this.log.info("init service end ; service num : " + (this.allMap.size() - size));
	}

	private void parseElementOfResource(Element resourceElement) throws Exception {
		if (resourceElement != null) {
			String fileName = resourceElement.attributeValue("import");
			initFile(fileName);
		}
	}

	public Object getObj(String key) {
		return this.allMap.get(key);
	}

	public <T> T get(String key) throws RuntimeException {
		Object o = getObj(key);
		return (T)o;
	}
}
