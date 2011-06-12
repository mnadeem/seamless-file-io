package com.nadeem.app.seamless.io.handler;

import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;

import org.junit.Test;

import com.nadeem.app.seamless.io.handler.FileReadingHandler;
import com.nadeem.app.seamless.io.handler.impl.FileReadingHandlerImpl;
import com.nadeem.app.seamless.io.session.DefaultFileSessionFactory;
import com.nadeem.app.seamless.io.session.ftp.FtpSessionFactory;


public class RegressionFileReadingHandlerTest {

	private static final String PASSWORD = "nadeem123";
	private static final String USER_NAME = "nadeem";
	private static final String HOST_NAME = "localhost";

	@Test
	public void fileSystemReadShouldBeSuccessful() throws Exception {
		DefaultFileSessionFactory factory = new DefaultFileSessionFactory(new File("d:\\a\\"));
		FileReadingHandler fileReadingHandler = new FileReadingHandlerImpl(factory);
		OutputStream file = fileReadingHandler.readFile("b\\nadeem.txt");
		assertNotNull(file.toString());
		System.out.println(file);
	}
	
	@Test
	public void ftpReadShouldBeSuccessful() throws Exception {
		FtpSessionFactory factory = getFTPSessionFactory();
		FileReadingHandler fileReadingHandler = new FileReadingHandlerImpl(factory);
		ByteArrayOutputStream file = (ByteArrayOutputStream) fileReadingHandler.readFile("nadee.txt");
		assertNotNull(file);		
		System.out.println(file);		
	}

	private FtpSessionFactory getFTPSessionFactory() {
		FtpSessionFactory factory = new FtpSessionFactory();
		factory.setHost(HOST_NAME);
		factory.setUsername(USER_NAME);
		factory.setPassword(PASSWORD);
		return factory;
	}
}
