package com.nadeem.app.seamless.io.session.ftp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.matchers.JUnitMatchers.hasItems;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;




public class FtpSessionTest {
	
	private static final String SOME_PATH		= "somePath";
	private static final String SOME_OTHER_PATH = "someOtherPath";
	
	@Mock
	private FTPClient mockedClient;
	private FtpSession ftpSession;
	

	@Before 
	public void doBeforeEachTestCase() {

		MockitoAnnotations.initMocks(this);
		ftpSession = new FtpSession(mockedClient);
	}
	
	@Test
	public void listFilesShouldReturnNull() throws Exception {

		FTPFile[] files = ftpSession.list(SOME_PATH);
		assertNull(files);
	}
	
	@Test 
	public void listFilesShouldReturnTwoFiles() throws Exception {
		
		FTPFile[] expected = {newFtpFile("a"), newFtpFile("b")};
		when(mockedClient.listFiles(SOME_PATH)).thenReturn(expected);
		FTPFile[] actual = ftpSession.list(SOME_PATH);
		assertThat(Arrays.asList(actual), hasItems(expected));
		assertEquals(expected.length, actual.length);
	}

	@Test(expected = RuntimeException.class)
	public void listFileShouldThrowException () throws Exception {

		when(mockedClient.listFiles(SOME_PATH)).thenThrow(new RuntimeException());
		ftpSession.list(SOME_PATH);		
	}

	@Test
	public void fileShouldBeDeleted() throws Exception {

		when(mockedClient.deleteFile(SOME_PATH)).thenReturn(true);
		boolean fileDeleted = ftpSession.remove(SOME_PATH);
		assertTrue(fileDeleted);
	}
	@Test
	public void fileShouldNotBeDeleted() throws Exception {

		when(mockedClient.deleteFile(SOME_PATH)).thenReturn(false);
		boolean fileDeleted = ftpSession.remove(SOME_PATH);
		assertFalse(fileDeleted);
	}

	@Test
	public void fileShouldBeReNamed() throws Exception {
		
		when(mockedClient.rename(SOME_PATH, SOME_OTHER_PATH)).thenReturn(true);
		boolean fileDeleted = ftpSession.rename(SOME_PATH, SOME_OTHER_PATH);
		assertTrue(fileDeleted);
	}

	@Test
	public void fileShouldNotBeReNamed() throws Exception {

		when(mockedClient.rename(SOME_PATH, SOME_OTHER_PATH)).thenReturn(false);
		boolean fileReNamed = ftpSession.rename(SOME_PATH, SOME_OTHER_PATH);
		assertFalse(fileReNamed);
	}

	@Test
	public void shouldCloseSuccessFully() throws Exception {

		ftpSession.close();
		verify(mockedClient, times(1)).disconnect();
	}
	@Test
	public void sessionIsOpen() throws Exception {
		
		assertTrue(ftpSession.isOpen());		
	}
	
	@Test
	public void sessionIsClosed() throws Exception {
		
		when(mockedClient.noop()).thenThrow(new RuntimeException());
		assertFalse(ftpSession.isOpen());		
	}

	@Test
	public void ftpReadShouldBeSuccessful() throws Exception {
		when(mockedClient.retrieveFile(anyString(), (OutputStream) anyObject())).thenReturn(true);
		ftpSession.read(SOME_PATH, null);
	}
	
	@Test
	public void ftpWriteShouldBeSuccessful() throws Exception {
		when(mockedClient.storeFile(anyString(), (InputStream) anyObject())).thenReturn(true);
		ftpSession.write(null, SOME_PATH);
	}

	private static FTPFile newFtpFile(String fileName) {
		
		FTPFile file = new FTPFile();
		file.setName(fileName);
		return file;
	}	
}
