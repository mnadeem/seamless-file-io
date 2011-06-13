package com.nadeem.app.seamless.io.session.ftp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import com.nadeem.app.seamless.io.session.Session;


public class FtpSession implements Session {

	private final FTPClient client;

	public FtpSession(FTPClient client) {
		this.client = client;
	}

	@SuppressWarnings({"unchecked"})
	public FTPFile[] list(String path) throws IOException {
		return this.client.listFiles(path);
	}

	public boolean remove(String path) throws IOException {
		return this.client.deleteFile(path);
	}

	public boolean rename(String pathFrom, String pathTo) throws IOException{
		client.deleteFile(pathTo);
		return client.rename(pathFrom, pathTo);
	}

	public void read(String path, OutputStream outputStream) throws IOException{
		boolean completed = this.client.retrieveFile(path, outputStream);
		throwExceptionIfNotCompleted("Failed to read from '" + path + "'", completed);
	}

	public void write(InputStream inputStream, String path) throws IOException{
		boolean completed = client.storeFile(path, inputStream);
		throwExceptionIfNotCompleted("Failed to write to '" + path + "'", completed);
	}


	public void close() {
		try {
			this.client.disconnect();
		} catch (Exception e) {
			//keep quiet
		}
	}

	public boolean isOpen() {
		try {
			client.noop();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private void throwExceptionIfNotCompleted(String message, boolean completed) throws IOException {
		if (!completed) {
			throw new IOException(message + ". Server replied with: " + client.getReplyString());
		}
	}
}
