package com.gxyj.test.commons.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;

public abstract class ServiceUnit {

	private Logger logger = Logger.getLogger(super.getClass());

	protected int _id = 0;
	private String logFileName;
	private File logFile;

	public void log(String log) {
		logger.info(new Date(System.currentTimeMillis()) + " " + log);
	}

	public void init(int id) {
		this._id = id;
	}

	public void logSuccInfo() {
		if (this.logFile == null) {
			return;
		}

		FileWriter fw = null;
		try {
			fw = new FileWriter(this.logFile);
			fw.write(System.currentTimeMillis() + " " + super.getClass().getName());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fw != null)
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	protected abstract void process();

	public ServiceUnit() {
		this.logFileName = ServiceConfigure.getInstance().getPropertyAsString("logFileName", "");
		if (this.logFileName.length() > 0) {
			this.logFile = new File(this.logFileName);

			if (!(this.logFile.exists())) {
				forceMkDir(this.logFile.getParentFile());
			}
			if (this.logFile.isDirectory())
				throw new RuntimeException("logFile is dir not file");
		}
	}

	private void forceMkDir(File f) {
		if ((f != null) && (!(f.exists()))) {
			log("file : " + f.getPath());

			forceMkDir(f.getParentFile());

			f.mkdir();
		}
	}

	public String getLogFileName() {
		return this.logFileName;
	}
}
