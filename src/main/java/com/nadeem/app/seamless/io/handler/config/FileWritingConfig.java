package com.nadeem.app.seamless.io.handler.config;


public class FileWritingConfig {

	public static final String FILE_SEPERATOR = System.getProperty("file.separator");

	private final String fileName;
	private final String destinationDirectory;

	private boolean autoCreateDirectory = true;

	public FileWritingConfig(String destinationDirectory, String fileName) {
		this.destinationDirectory = destinationDirectory;
		this.fileName = fileName;
	}

	public boolean autoCreateDirectory() {
		return autoCreateDirectory;
	}
	public void setAutoCreateDirectory(boolean autoCreateDirectory) {
		this.autoCreateDirectory = autoCreateDirectory;
	}
	public String getDestinationDirectory() {
		return destinationDirectory;
	}
	public String getFileName() {
		return fileName;
	}

	public String getAbsoluteFileName() {
		return destinationDirectory + FILE_SEPERATOR + fileName;
	}
}
