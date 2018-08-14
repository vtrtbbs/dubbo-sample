package com.gxyj.test.commons.service;

import org.apache.log4j.Logger;

public class ServiceThread implements Runnable {

	private Logger logger = Logger.getLogger(ServiceThread.class);
	private long _sleepTime;
	private ServiceUnit _core;

	public ServiceThread(ServiceUnit core, long sleepTime) {
		this._core = core;
		this._sleepTime = sleepTime;
	}

	@Override
	public void run() {
		while (true) {
			this._core.process();
			this._core.logSuccInfo();
			if (this._sleepTime > 0L) {
				try {
					Thread.sleep(this._sleepTime);
				} catch (InterruptedException e) {
					this.logger.error("Interrupted? Then quit.");
					return;
				}
			}
			this.logger.info("Thread wake up now!");
		}
	}

}
