package com.nadeem.app.seamless.io.handler.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import com.nadeem.app.seamless.io.handler.FileReadingHandler;
import com.nadeem.app.seamless.io.session.Session;
import com.nadeem.app.seamless.io.session.SessionFactory;

public class FileReadingHandlerImpl implements FileReadingHandler {

	private final SessionFactory sessionFactory;

	public FileReadingHandlerImpl(final SessionFactory newSessionFactory) {
		this.sessionFactory = newSessionFactory;
	}

	public ByteArrayOutputStream readFile(final String source) {
		Session session = null;
		try {
			session = this.sessionFactory.getSession();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			session.read(source, outputStream);
			return outputStream;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(session);
			// no need to close outputStream
		}
	}
}
