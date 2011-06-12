package com.nadeem.app.seamless.io.handler;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.nadeem.app.seamless.io.handler.FileWritingHandler;
import com.nadeem.app.seamless.io.handler.config.FileWritingConfig;
import com.nadeem.app.seamless.io.handler.impl.FileWritingHandlerImpl;
import com.nadeem.app.seamless.io.session.ftp.FtpSessionFactory;

public class RegressionFileWritingHandlerTest {

	private static final String PASSWORD = "nadeem123";
	private static final String USER_NAME = "nadeem";
	private static final String HOST_NAME = "localhost";

	@Test
	public void ftpWriteShouldBeSuccessful() throws Exception {
		FtpSessionFactory factory 			  = getFTPSessionFactory();
		FileWritingHandler fileWritingHandler = new FileWritingHandlerImpl(factory);
		fileWritingHandler.writeToFile(IOUtils.toInputStream("Hello"), getFileConfig());
			
	}

	private FileWritingConfig getFileConfig() {
		FileWritingConfig config = new FileWritingConfig("test", "nadee.txt");
		return config;
	}

	private FtpSessionFactory getFTPSessionFactory() {
		FtpSessionFactory factory = new FtpSessionFactory();
		factory.setHost(HOST_NAME);
		factory.setUsername(USER_NAME);
		factory.setPassword(PASSWORD);
		return factory;
	}
}
