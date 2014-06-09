package kata.text.anagram;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWordRepositoryTest
{
	private static final String REPOSITORY_TEST_FILE_PATH = "dummy.txt";
	private static final String WORD_DELIMITER = System.lineSeparator();

	@BeforeClass
	public static void oneTimeSetUp() throws Exception
	{
		File testFile = new File(REPOSITORY_TEST_FILE_PATH);
		
		boolean isCreated = testFile.createNewFile();
		
		assertThat(isCreated, is(equalTo(true)));
	}
	
	@AfterClass
	public static void oneTimeTearDown() throws Exception
	{
		File testFile = new File(REPOSITORY_TEST_FILE_PATH);
		
		boolean isDeleted = testFile.delete();
		
		assertThat(isDeleted, is(equalTo(true)));
	}
	
	@After
	public void tearDown() throws Exception
	{
		truncateFile();
	}

	@Test(expected=NullPointerException.class)
	public void testFileWordRepository_WhenFileArgumentIsNull()
	{
		new FileWordRepository(null);
	}
	
	@Test(expected=Exception.class)
	public void testFileWordRepository_WhenFileDoesNotExist()
	{
		new FileWordRepository(new File("NotExistingFile.txt"));
	}
	
	public void testFileWordRepository_WhenFileExists() throws Exception
	{
		new FileWordRepository(new File(REPOSITORY_TEST_FILE_PATH));
	}

	@Test
	public void testFindAllWords_WhenFileIsEmpty()
	{
		FileWordRepository fileWordRepository = new FileWordRepository(new File(REPOSITORY_TEST_FILE_PATH));
		
		List<Word> allWords = fileWordRepository.findAllWords();
		
		assertThat(allWords, is(nullValue()));
	}
	
	@Test
	public void testFindAllWords_WhenFileContainsSingleItem() throws Exception
	{
		writeToFile("aaa");
		
		FileWordRepository fileWordRepository = new FileWordRepository(new File(REPOSITORY_TEST_FILE_PATH));
		
		List<Word> allWords = fileWordRepository.findAllWords();
		
		assertThat(allWords, is(notNullValue()));
		assertThat(allWords.size(), is(equalTo(1)));
		
		Word word = allWords.get(0);
		
		assertThat(word.toString(), is(equalTo("aaa")));
	}
	
	@Test
	public void testFindAllWords_WhenFileContainsMultipleItems() throws Exception
	{
		writeToFile("aaa" + WORD_DELIMITER + "bbb" + WORD_DELIMITER + "aba");
		
		FileWordRepository fileWordRepository = new FileWordRepository(new File(REPOSITORY_TEST_FILE_PATH));
		
		List<Word> allWords = fileWordRepository.findAllWords();
		
		assertThat(allWords, is(notNullValue()));
		assertThat(allWords.size(), is(equalTo(3)));
		
		Word word0 = allWords.get(0);
		Word word1 = allWords.get(1);
		Word word2 = allWords.get(2);
		
		assertThat(word0.toString(), is(equalTo("aaa")));
		assertThat(word1.toString(), is(equalTo("bbb")));
		assertThat(word2.toString(), is(equalTo("aba")));
	}

	@Test
	public void testFindDistinctWords_WhenFileIsEmpty()
	{
		FileWordRepository fileWordRepository = new FileWordRepository(new File(REPOSITORY_TEST_FILE_PATH));
		
		Set<Word> distinctWords = fileWordRepository.findDistinctWords();
		
		assertThat(distinctWords, is(nullValue()));
	}
	
	@Test
	public void testFindDistinctWords_WhenFileContainsSingleItem() throws Exception
	{
		writeToFile("aaa");
		
		FileWordRepository fileWordRepository = new FileWordRepository(new File(REPOSITORY_TEST_FILE_PATH));
		
		Set<Word> distinctWords = fileWordRepository.findDistinctWords();
		
		assertThat(distinctWords, is(notNullValue()));
		assertThat(distinctWords.size(), is(equalTo(1)));
		
		Word word = new ArrayList<Word>(distinctWords).get(0);
		
		assertThat(word.toString(), is(equalTo("aaa")));
	}

	@Test
	public void testFindDistinctWords_WhenFileContainsMultipleItem_And_ContainsNoDuplicates() throws Exception
	{
		writeToFile("aaa" + WORD_DELIMITER + "bbb" + WORD_DELIMITER + "aba");
		
		FileWordRepository fileWordRepository = new FileWordRepository(new File(REPOSITORY_TEST_FILE_PATH));
		
		Set<Word> distinctWords = fileWordRepository.findDistinctWords();
		
		assertThat(distinctWords, is(notNullValue()));
		assertThat(distinctWords.size(), is(equalTo(3)));
		
		// convert Set to a List for random-access
		List<Word> distinctWordList = new ArrayList<Word>(distinctWords);
		
		Word word0 = distinctWordList.get(0);
		Word word1 = distinctWordList.get(1);
		Word word2 = distinctWordList.get(2);
		
		assertThat(word0.toString(), isOneOf("aaa", "bbb", "aba"));
		assertThat(word1.toString(), isOneOf("aaa", "bbb", "aba"));
		assertThat(word2.toString(), isOneOf("aaa", "bbb", "aba"));
	}
	
	@Test
	public void testFindDistinctWords_WhenFileContainsMultipleItem_And_ContainsDuplicates() throws Exception
	{
		writeToFile("aaa" + WORD_DELIMITER + "bbb" + WORD_DELIMITER + "aba" + WORD_DELIMITER + "aaa" + WORD_DELIMITER + "aba");
		
		FileWordRepository fileWordRepository = new FileWordRepository(new File(REPOSITORY_TEST_FILE_PATH));
		
		Set<Word> distinctWords = fileWordRepository.findDistinctWords();
		
		assertThat(distinctWords, is(notNullValue()));
		assertThat(distinctWords.size(), is(equalTo(3)));
		
		// convert Set to a List for random-access
		List<Word> distinctWordList = new ArrayList<Word>(distinctWords);
		
		Word word0 = distinctWordList.get(0);
		Word word1 = distinctWordList.get(1);
		Word word2 = distinctWordList.get(2);
		
		assertThat(word0.toString(), isOneOf("aaa", "bbb", "aba"));
		assertThat(word1.toString(), isOneOf("aaa", "bbb", "aba"));
		assertThat(word2.toString(), isOneOf("aaa", "bbb", "aba"));
	}
	
	private void writeToFile(String content) throws Exception
	{
		Writer fileWriter = new BufferedWriter(new FileWriter(new File(REPOSITORY_TEST_FILE_PATH)));
		
		try
		{
			fileWriter.write(content);
		}
		finally
		{
			fileWriter.close();
		}
	}

	private void truncateFile() throws Exception
	{
		writeToFile("");
	}
}
