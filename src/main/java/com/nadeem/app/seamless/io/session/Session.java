package com.nadeem.app.seamless.io.session;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Session extends Closeable {

	boolean remove(String path) throws IOException;

	<F> F[] list(String path) throws IOException;

	void read(String source, OutputStream outputStream) throws IOException;

	void write(InputStream inputStream, String destination) throws IOException;

	boolean rename(String pathFrom, String pathTo) throws IOException;

	boolean isOpen();
}
