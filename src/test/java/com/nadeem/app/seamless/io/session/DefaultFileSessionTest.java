package com.nadeem.app.seamless.io.session;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.matchers.JUnitMatchers.hasItems;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;

public class DefaultFileSessionTest {
	
	private static final String SOME_PATH		= "somePath";
	private static final String SOME_OTHER_PATH = "someOtherPath";
	private static final String DATA_CONTENT 	= "Blah Blah";
	
	@Mock private File mockedFile;
	@Mock private File mockedParent;
	private InputStream mockedInputStream;
	private OutputStream mockedOutputStream;
	private DefaultFileSession fileSession;

	@Before 
	public void doBeforeEachTestCase() {

		MockitoAnnotations.initMocks(this);
		mockedInputStream	= new ByteArrayInputStream(DATA_CONTENT.getBytes());
		mockedOutputStream	= new ByteArrayOutputStream();
		fileSession 		= new MockedDefaultFileSession(mockedParent);
		Whitebox.setInternalState(mockedParent, "path", "path");
	}
	
	@Test 
	public void listFilesShouldReturnNull() throws Exception {

		File[] files = fileSession.list(SOME_PATH);
		assertNull(files);
	}

	@Test 
	public void listFilesShouldReturnTwoFiles() throws Exception {
		
		File[] expected = {new File("a"), new File("b")};
		when(mockedParent.listFiles()).thenReturn(expected);
		File[] actual = fileSession.list(SOME_PATH);
		assertThat(Arrays.asList(actual), hasItems(expected));
		assertEquals(expected.length, actual.length);
	}

	@Test(expected= RuntimeException.class)
	public void listFileShouldThrowException () throws Exception {

		when(mockedParent.listFiles()).thenThrow(new RuntimeException());
		fileSession.list(SOME_PATH);		
	}
	
	@Test
	public void fileShouldBeDeleted() throws Exception {
		
		when(mockedFile.exists()).thenReturn(true);
		when(mockedFile.delete()).thenReturn(true);
		boolean fileDeleted = fileSession.remove(SOME_PATH);
		assertTrue(fileDeleted);
	}

	@Test
	public void fileShouldNotBeDeleted() throws Exception {

		when(mockedFile.delete()).thenReturn(false);
		boolean fileDeleted = fileSession.remove(SOME_PATH);
		assertFalse(fileDeleted);
	}
	
	@Test
	public void fileShouldBeReNamed() throws Exception {
		
		when(mockedFile.exists()).thenReturn(true);
		when(mockedFile.renameTo((File) anyObject())).thenReturn(true);
		boolean fileDeleted = fileSession.rename(SOME_PATH, SOME_OTHER_PATH);
		assertTrue(fileDeleted);
	}

	@Test
	public void fileShouldNotBeReNamed() throws Exception {

		when(mockedFile.exists()).thenReturn(false);
		when(mockedFile.renameTo((File) anyObject())).thenReturn(false);
		boolean fileReNamed = fileSession.rename(SOME_PATH, SOME_OTHER_PATH);
		assertFalse(fileReNamed);
	}
	
	@Test 
	public void closeShouldNotDoAnything() throws Exception {
		fileSession.close();
	}
	@Test 
	public void isOpenShouldReturnFalse() throws Exception {
		
		assertFalse(fileSession.isOpen());
	}
	
	@Test
	public void fileReadShouldBeSuccessful() throws Exception {
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		fileSession.read(SOME_PATH, outputStream);
		assertNotNull(outputStream.toString());
		assertEquals(DATA_CONTENT, outputStream.toString());
	}
	
	@Test
	public void fileWriteShouldBeSuccessful() throws Exception {
		
		fileSession.write(mockedInputStream, SOME_PATH);
		assertNotNull(mockedOutputStream.toString());
		assertEquals(DATA_CONTENT, mockedOutputStream.toString());
	}

	private class MockedDefaultFileSession extends DefaultFileSession {

		public MockedDefaultFileSession(File newParent) {
			super(newParent);
		}
		protected File createNewFile(String path) {
			return mockedFile;
		}
		protected InputStream createFileInputStream(File fileToRead) throws FileNotFoundException {
			return mockedInputStream;  
		}
		protected OutputStream createFileOutPutStream(File destinationFile) throws FileNotFoundException {
			return mockedOutputStream;
		}
	}
}
