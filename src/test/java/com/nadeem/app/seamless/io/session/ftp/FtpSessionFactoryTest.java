package com.nadeem.app.seamless.io.session.ftp;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import org.apache.commons.net.ftp.FTPClient;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class FtpSessionFactoryTest {
	
	@Mock
	private FTPClient client;

	private FtpSessionFactory factory;
	
	@Before
	public void doBeforeEachTestCase() {
		MockitoAnnotations.initMocks(this);
		factory = new MockedFtpSessionFactory();
	}

	@Test
	public void sessionShouldNotBeNull() throws Exception {
		when(client.getReplyCode()).thenReturn(200);
		when(client.login(anyString(), anyString())).thenReturn(true);
		assertNotNull(factory.getSession());		
	}
	
	@Test
	public void shouldReturnDefaultFileSession() throws Exception {
		when(client.getReplyCode()).thenReturn(200);
		when(client.login(anyString(), anyString())).thenReturn(true);
		assertThat(factory.getSession(), instanceOf(FtpSession.class));
	}
	
	private class MockedFtpSessionFactory extends FtpSessionFactory {
		protected FTPClient createClientInstance() {
			return client;
		}
	}
}
