package com.nadeem.app.seamless.io.session.ftp;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

import com.nadeem.app.seamless.io.session.Session;
import com.nadeem.app.seamless.io.session.SessionFactory;

public class FtpSessionFactory implements SessionFactory {

	private int port 		= FTP.DEFAULT_PORT;	
	private int fileType 	= FTP.ASCII_FILE_TYPE;
	private int clientMode 	= FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE;
	private int bufferSize 	= 2048;

	private String username;
	private String host;
	private String password;
	private FTPClientConfig config;


	public void setConfig(FTPClientConfig config) {
		this.config = config;
	}

	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setUsername(String user) {
		this.username = user;
	}

	public void setPassword(String pass) {
		this.password = pass;
	}

	/**
	 * File types defined by {@link org.apache.commons.net.ftp.FTP} constants.
	 */
	public void setFileType(int fileType) {
		this.fileType = fileType;
	}

	/**
	 *Connection modes defined by {@link org.apache.commons.net.ftp.FTPClient}.
	 */
	public void setClientMode(int clientMode) {	
		this.clientMode = clientMode;
	}

	public Session getSession() {
		try {
			return doGetSession();
		}
		catch (Exception e) {
			throw new IllegalStateException("failed to create FTPClient", e);
		}
	}

	private Session doGetSession() throws SocketException, IOException {
		FTPClient client = this.createClient();
		if (client == null) {
			return null;
		}
		return new FtpSession(client);
	}

	private FTPClient createClient() throws SocketException, IOException { 
		FTPClient client = this.createClientInstance();
		client.configure(this.config);

		this.postProcessClientBeforeConnect(client);
		doConnect(client);
		doLogin(client);
		this.postProcessClientAfterConnect(client);

		doSetup(client);

		return client;
	}

	private void doSetup(final FTPClient client) throws IOException {
		this.updateClientMode(client);
		client.setFileType(fileType);
		client.setBufferSize(bufferSize);
	}

	private void doLogin(final FTPClient client) throws IOException {
		if (!client.login(username, password)) {
			throw new IllegalStateException("Login failed. The response from the server is: " +
					client.getReplyString());
		}
	}

	private void doConnect(final FTPClient client) throws SocketException, IOException {
		client.connect(host, port);

		if (!FTPReply.isPositiveCompletion(client.getReplyCode())) {
			throw new RuntimeException("Connecting to server [" +
					host + ":" + port + "] failed. Please check the connection.");
		}
	}

	/**
	 * Sets the mode of the connection. Only local modes are supported.
	 */
	private void updateClientMode(final FTPClient client) {
		switch (this.clientMode) {
			case FTPClient.ACTIVE_LOCAL_DATA_CONNECTION_MODE:
				client.enterLocalActiveMode();
				break;
			case FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE:
				client.enterLocalPassiveMode();
				break;
			default:
				break;
		}
	}

	protected FTPClient createClientInstance() {
		return new FTPClient();
	}

	protected void postProcessClientBeforeConnect(FTPClient client) throws IOException {}
	protected void postProcessClientAfterConnect(FTPClient t) throws IOException {}

}
