package com.nadeem.app.seamless.io.session;

import java.io.File;

public class DefaultFileSessionFactory implements SessionFactory {

	private final File root;

	public DefaultFileSessionFactory(File newRoot) {
		this.root = newRoot;
	}

	public Session getSession() {
		return new DefaultFileSession(root);
	}
}
