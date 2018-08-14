package com.gxyj.test.commons.handler;

import java.util.List;

import org.apache.log4j.Logger;

import com.gxyj.test.commons.factory.ContextFactory;

public class DefaultServiceInit implements ServiceInit {
	private static Logger log = Logger.getLogger(DefaultServiceInit.class);
	private List<String> configResourceList;
	private List<String> wrapResourceList;

	@Override
	public void initService() throws RuntimeException {
		try {
			for (String configResource : this.configResourceList) {
				log.info("------configResource------->"+configResource);
				ContextFactory.getInstance().initFile(configResource);
			}

			for (String wrapResource : this.wrapResourceList) {
				GenericWrapManage.getInstance().initFile(wrapResource);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void setConfigResourceList(List<String> configResourceList) {
		this.configResourceList = configResourceList;
	}

	public void setWrapResourceList(List<String> wrapResourceList) {
		this.wrapResourceList = wrapResourceList;
	}

}
