package com.nadeem.app.seamless.io.handler.impl;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.nadeem.app.seamless.io.handler.FileWritingHandler;
import com.nadeem.app.seamless.io.handler.config.FileWritingConfig;
import com.nadeem.app.seamless.io.session.Session;
import com.nadeem.app.seamless.io.session.SessionFactory;

public class FileWritingHandlerImpl implements FileWritingHandler {

	private static final String TEMPORARY_FILE_SUFFIX =".writing";

	private final SessionFactory sessionFactory;

	public FileWritingHandlerImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public boolean writeToFile(InputStream inputStream, FileWritingConfig config) {
		Session session = null;
		try {
			String tempFileName = config.getAbsoluteFileName() + TEMPORARY_FILE_SUFFIX;
			session = this.sessionFactory.getSession();
			session.write(inputStream, tempFileName);
			session.rename(tempFileName, config.getAbsoluteFileName());
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(session);
		}
		return false;
	}
}
