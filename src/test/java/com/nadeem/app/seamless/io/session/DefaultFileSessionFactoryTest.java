package com.nadeem.app.seamless.io.session;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;


public class DefaultFileSessionFactoryTest {
	
	private DefaultFileSessionFactory factory;
	
	@Before
	public void doBeforeEachTestCase() {
		factory = new DefaultFileSessionFactory(mock(File.class));
	}

	@Test
	public void sessionShouldNotBeNull() {
		assertNotNull(factory.getSession());		
	}
	
	@Test
	public void shouldReturnDefaultFileSession() throws Exception {
		assertThat(factory.getSession(), instanceOf(DefaultFileSession.class));
	}
}
