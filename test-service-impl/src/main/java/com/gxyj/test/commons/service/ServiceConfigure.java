package com.gxyj.test.commons.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

public class ServiceConfigure {

	private static Logger logger = Logger.getLogger(ServiceConfigure.class);

	private static ServiceConfigure _instance = new ServiceConfigure();

	private boolean ready = false;
	private HashMap maps = new HashMap();

	public static ServiceConfigure getInstance() {
		return _instance;
	}

	public Object getProperty(String key) {
		Object value = this.maps.get(key);
		return value;
	}

	public String getPropertyAsString(String key, String def) {
		Object value = getProperty(key);
		if (value == null)
			return def;

		return value.toString();
	}

	public Integer getPropertyAsInteger(String key, int def) {
		Object value = getProperty(key);
		if (value == null)
			return Integer.valueOf(def);

		return Integer.valueOf(value.toString());
	}

	public Long getPropertyAsLong(String key, long def) {
		Object value = getProperty(key);
		if (value == null)
			return Long.valueOf(def);

		return Long.valueOf(value.toString());
	}

	public boolean getPropertyAsBoolean(String key, boolean def) {
		Object value = getProperty(key);
		if (value == null)
			return def;

		return Boolean.valueOf(value.toString()).booleanValue();
	}

	public void setPropertyInLine(String readline) {
		if (readline.length() <= 3)
			return;

		int keyEnd = readline.indexOf("=");
		if (keyEnd == -1)
			return;

		String key = readline.substring(0, keyEnd).trim();
		String value = readline.substring(keyEnd + 1).trim();

		if (this.maps == null)
			this.maps = new HashMap();
		this.maps.put(key, value);
	}

	public void show() {
		logger.info("Load configures:");
		Iterator iter = this.maps.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) (Map.Entry) iter.next();
			logger.info("Has key '" + entry.getKey() + "' = '" + ((entry.getValue() == null) ? "null" : entry.getValue()) + "'");
		}
		logger.info("End configures.");
	}

	public void load(InputStream is) throws IOException {
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			isr = new InputStreamReader(is, "gbk");
			br = new BufferedReader(isr);

			String line = null;

			while ((line = br.readLine()) != null)
				setPropertyInLine(line);
		} catch (IOException e) {
			e.printStackTrace();
			this.ready = false;
		} finally {
			br.close();
			isr.close();
			is.close();
		}
	}

	public void load(String filename) {
		File file = new File(filename);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			load(fis);
			this.ready = true;
		} catch (Exception e) {
			e.printStackTrace();
			this.ready = false;
		}

		if (!(this.ready))
			return;
		show();
	}

	public boolean isReady() {
		return (this.maps == null);
	}

}
