package com.gxyj.test.commons.factory;

import java.io.IOException;
import java.io.InputStream;

import com.ibatis.common.resources.Resources;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

public class DbHelper {

	private static Logger log = Logger.getLogger(DbHelper.class);

	public static Document parseXml(String xml) {
		Document doc = null;
		InputStream inputStream = null;
		try {
			SAXReader reader = new SAXReader();
			inputStream = Resources.getResourceAsStream(xml);
			doc = reader.read(inputStream);
			log.info("parseXML:" + xml);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return doc;
	}

}
