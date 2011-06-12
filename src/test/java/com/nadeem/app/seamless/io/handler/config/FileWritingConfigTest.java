package com.nadeem.app.seamless.io.handler.config;

import org.junit.Test;
import static org.junit.Assert.*;

public class FileWritingConfigTest {
	
	@Test
	public void testAbsoluteFileName() {
		String destinationDirectory = "\\a";
		String fileName				= "test";
		FileWritingConfig config = new FileWritingConfig(destinationDirectory, fileName);
		assertEquals("\\a\\test", config.getAbsoluteFileName());
	}
}
