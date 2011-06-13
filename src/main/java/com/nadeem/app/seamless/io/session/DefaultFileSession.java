package com.nadeem.app.seamless.io.session;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

public class DefaultFileSession implements Session {

	private File parent;

	public DefaultFileSession(File newParent) {
		this.parent = newParent;
	}

	@SuppressWarnings({"unchecked"})
	public File[] list(String path) throws IOException {
		return this.parent.listFiles();
	}

	public boolean remove(String path) throws IOException {
		File fileToBeDeleted = createNewFile(path);
		if (fileToBeDeleted.exists()) {
			return fileToBeDeleted.delete();
		}
		return false;
	}

	public boolean rename(String pathFrom, String pathTo) throws IOException {
		File fileToBeRenamed = createNewFile(pathFrom);
		if (fileToBeRenamed.exists()) {
			return fileToBeRenamed.renameTo(new File(parent, pathTo));
		}
		return false;
	}

	public void read(String path, OutputStream outputStream) throws IOException {
		File fileToRead = createNewFile(path);
		InputStream inputStream  = createFileInputStream(fileToRead);
		IOUtils.copyLarge(inputStream, outputStream);
		IOUtils.closeQuietly(inputStream);
	}

	protected InputStream createFileInputStream(File fileToRead) throws FileNotFoundException {
		return new FileInputStream(fileToRead);
	}

	public void write(InputStream inputStream, String path) throws IOException {
		File destinationFile 	  = createNewFile(path);
		OutputStream outputStream = createFileOutPutStream(destinationFile);
		IOUtils.copyLarge(inputStream, outputStream);
		IOUtils.closeQuietly(outputStream);
	}

	protected OutputStream createFileOutPutStream(File destinationFile) throws FileNotFoundException {
		return new FileOutputStream(destinationFile);
	}

	protected File createNewFile(String path) {
		return new File(parent, path);
	}

	public void close() {
		// Don't do anything
	}

	public boolean isOpen() {
		return false;
	}
}
