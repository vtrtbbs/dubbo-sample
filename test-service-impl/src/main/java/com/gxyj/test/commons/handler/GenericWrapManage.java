package com.gxyj.test.commons.handler;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.gxyj.test.commons.factory.DbHelper;

public class GenericWrapManage {

	private static Logger logger = Logger.getLogger(GenericWrapManage.class);

	private static GenericWrapManage instance = new GenericWrapManage();
	private static final String TYPE_NEW = "new";
	private static final String TYPE_SPRING = "spring";
	private static final String TYPE_LOCAL = "local";
	private Map<String, GenericWrap> map = new HashMap();

	private Map<String, GenericWrap> wrapMap = new HashMap();

	public static GenericWrapManage getInstance() {
		return instance;
	}

	public void initFile(String fileName) throws Exception {
		Document doc = DbHelper.parseXml(fileName);

		if (doc != null) {
			Element rootElement = doc.getRootElement();

			for (Iterator localIterator = rootElement.elements().iterator(); localIterator.hasNext();) {
				Object o = localIterator.next();
				Element e = (Element) o;

				String name = e.getName();

				if (name != null)
					if (name.equalsIgnoreCase("wraps"))
						parseWraps(e);
					else if (name.equalsIgnoreCase("wrapManage"))
						parseManage(e);
					else if (name.equalsIgnoreCase("resource"))
						parseResource(e);
			}
		}
	}

	private void parseWraps(Element wrapsElement) throws Exception {
		if ((wrapsElement != null) && (wrapsElement.hasContent()))
			for (Iterator localIterator1 = wrapsElement.elements("wrap").iterator(); localIterator1.hasNext();) {
				Object o = localIterator1.next();
				Element wrapElement = (Element) o;

				String id = wrapElement.attributeValue("id");
				String clazz = wrapElement.attributeValue("class");

				if ((id == null) || (id.length() == 0)) {
					id = clazz;
				}

				GenericWrap wrap = null;

				wrap = (GenericWrap) (GenericWrap) Class.forName(clazz).newInstance();

				for (Iterator localIterator2 = wrapElement.elements("property").iterator(); localIterator2.hasNext();) {
					Object o1 = localIterator2.next();
					Element propertyElement = (Element) o1;

					Field field = wrap.getClass().getDeclaredField(propertyElement.attributeValue("name"));

					boolean accessible = true;

					if (!(field.isAccessible())) {
						accessible = false;
						field.setAccessible(true);
					}

					field.set(wrap, propertyElement.attributeValue("value"));

					if (!(accessible)) {
						field.setAccessible(false);
					}
				}

				wrap.init();

				this.wrapMap.put(id, wrap);
			}
	}

	private void parseManage(Element manageElement) throws Exception {
		if ((manageElement != null) && (manageElement.hasContent()))
			for (Iterator localIterator = manageElement.elements("register").iterator(); localIterator.hasNext();) {
				Object o = localIterator.next();
				Element registerElement = (Element) o;

				String beanName = registerElement.attributeValue("beanName");
				String wrapClassName = registerElement.attributeValue("wrapClassName");
				String wrapId = registerElement.attributeValue("wrapId");
				String type = registerElement.attributeValue("type");

				registerWrap(beanName, wrapClassName, wrapId, type);
			}
	}

	private void registerWrap(String beanName, String wrapClassName, String wrapId, String type) throws Exception {
		GenericWrap wrap = null;

		if ("new".equals(type)) {
			Object o = Class.forName(wrapClassName).newInstance();

			wrap = (GenericWrap) o;

			wrap.init();
		} else if ("local".equals(type)) {
			if ((wrapId == null) || (wrapId.length() == 0)) {
				wrapId = wrapClassName;
			}

			wrap = (GenericWrap) this.wrapMap.get(wrapId);
		} else {
			"spring".equals(type);
		}

		this.map.put(beanName, wrap);
	}

	private void parseResource(Element resourceElement) throws Exception {
		if (resourceElement != null) {
			String fileName = resourceElement.attributeValue("import");

			initFile(fileName);
		}
	}

	public void wrapBeforeDbOp(Object o) {
		if (o != null) {
			logger.debug("GenericWrapManage start to wrap " + o.getClass().getSimpleName());
			GenericWrap wrap = (GenericWrap) this.map.get(o.getClass().getName());

			if (wrap != null)
				wrap.wrapBeforeDbOp(o);
		} else {
			logger.debug("GenericWrapManage start to wrap obj of null");
		}
	}

	public void wrapAfterDbOp(Object o) {
		if (o != null) {
			logger.debug("GenericWrapManage start to wrap " + o.getClass().getSimpleName());
			GenericWrap wrap = (GenericWrap) this.map.get(o.getClass().getName());

			if (wrap != null)
				wrap.wrapAfterDbOp(o);
		} else {
			logger.debug("GenericWrapManage start to wrap obj of null");
		}
	}
}
