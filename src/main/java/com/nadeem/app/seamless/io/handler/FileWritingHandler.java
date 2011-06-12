package com.nadeem.app.seamless.io.handler;

import java.io.InputStream;

import com.nadeem.app.seamless.io.handler.config.FileWritingConfig;

public interface FileWritingHandler {

	boolean writeToFile(InputStream inputStream, FileWritingConfig config);
}
