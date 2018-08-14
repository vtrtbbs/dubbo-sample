package com.gxyj.test.commons.service;

import org.apache.log4j.Logger;

public class ServiceMain {

	private Logger logger = Logger.getLogger(ServiceMain.class);

	public ServiceConfigure configure = ServiceConfigure.getInstance();

	public ServiceMain(String[] args) {
		boolean nextConfigure = false;
		for (int i = 0; i < args.length; ++i) {
			if (nextConfigure) {
				this.configure.load(args[i]);
			} else {
				if (!(args[i].equals("-c")))
					continue;
				nextConfigure = true;
			}
		}
		if ((this.configure == null) || (!(this.configure.isReady()))) {
			usage();
			return;
		}

		String coreName = this.configure.getPropertyAsString("coreName", "com.baihe.commons.service.ServiceUnit");

		long sleepTime = this.configure.getPropertyAsLong("sleepTime", 0L).longValue();
		int coreNumber = this.configure.getPropertyAsInteger("coreNumber", 1).intValue();

		for (long i = 0L; i < coreNumber; i += 1L) {
			Class coreClass;
			ServiceUnit coreUnit;
			try {
				coreClass = Class.forName(coreName);
				coreUnit = (ServiceUnit) coreClass.newInstance();
			} catch (ClassNotFoundException e) {
				this.logger.error("Can not find the class");
				e.printStackTrace();
				return;
			} catch (InstantiationException e) {
				this.logger.error("Can not initialize the class");
				e.printStackTrace();
				return;
			} catch (IllegalAccessException e) {
				this.logger.error("Can not legally access the class");
				e.printStackTrace();
				return;
			}

			coreUnit.init((int) i + 1);
			Thread t = new Thread(new ServiceThread(coreUnit, sleepTime));
			t.start();
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException localInterruptedException) {
			}
		}
	}

	public void usage() {
		this.logger.info("Can not find configure file.");
		this.logger.info("Usage is:");
		this.logger.info("java -jar jar_filename -c configure_filename");
	}

	public static void main(String[] args) {
		ServiceMain service = new ServiceMain(args);
	}
}
