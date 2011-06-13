package com.nadeem.app.seamless.io;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.nadeem.app.seamless.io.handler.config.FileWritingConfigTest;
import com.nadeem.app.seamless.io.session.DefaultFileSessionFactoryTest;
import com.nadeem.app.seamless.io.session.DefaultFileSessionTest;
import com.nadeem.app.seamless.io.session.ftp.FtpSessionFactoryTest;
import com.nadeem.app.seamless.io.session.ftp.FtpSessionTest;

@RunWith(Suite.class)
@SuiteClasses(
	{DefaultFileSessionTest.class,
	DefaultFileSessionFactoryTest.class,
	FtpSessionFactoryTest.class,
	FtpSessionTest.class,
	FileWritingConfigTest.class})
public class AllTests {	

}
