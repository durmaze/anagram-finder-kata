package kata.text.anagram;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import kata.text.utils.IHttpClient;
import kata.text.utils.SimpleHttpClient;

import org.junit.Test;

public class WebWordRepositoryTest
{
	private static final String REPOSITORY_TEST_URL = "http://www.google.com";
	private static final String WORD_DELIMITER = "\n";
	
	@Test(expected=NullPointerException.class)
	public void testWebWordRepository_WhenUrlArgumentIsNull()
	{
		new WebWordRepository(null, new SimpleHttpClient(3000, 3000));
	}

	@Test(expected=NullPointerException.class)
	public void testWebWordRepository_WhenHttpClientArgumentIsNull() throws MalformedURLException
	{
		new WebWordRepository(new URL(REPOSITORY_TEST_URL), null);
	}

	@Test
	public void testWebWordRepository_WhenArgumentsAreSupplied() throws MalformedURLException
	{
		new WebWordRepository(new URL(REPOSITORY_TEST_URL), new SimpleHttpClient(3000, 3000));
	}
	
	@Test
	public void testFindAllWords_WhenWebContentIsEmpty() throws Exception
	{
		URL resourceUrl = new URL(REPOSITORY_TEST_URL);
		
		// mock IHttpClient
		IHttpClient mockHttpClient = mock(IHttpClient.class);
		when(mockHttpClient.getContent(resourceUrl)).thenReturn("");
		
		WebWordRepository webWordRepository = new WebWordRepository(resourceUrl, mockHttpClient);
		
		List<Word> allWords = webWordRepository.findAllWords();
		
		// assert results
		assertThat(allWords, is(nullValue()));
		
		// verify interactions
		verify(mockHttpClient).getContent(resourceUrl);
	}
	
	@Test
	public void testFindAllWords_WhenWebContentContainsSingleItem() throws Exception
	{
		URL resourceUrl = new URL(REPOSITORY_TEST_URL);
		
		// mock IHttpClient
		IHttpClient mockHttpClient = mock(IHttpClient.class);
		when(mockHttpClient.getContent(resourceUrl)).thenReturn("aaa");
		
		WebWordRepository webWordRepository = new WebWordRepository(resourceUrl, mockHttpClient);
		
		List<Word> allWords = webWordRepository.findAllWords();
		
		// assert results
		assertThat(allWords, is(notNullValue()));
		assertThat(allWords.size(), is(equalTo(1)));
		
		Word word = allWords.get(0);
		
		assertThat(word.toString(), is(equalTo("aaa")));
		
		// verify interactions
		verify(mockHttpClient).getContent(resourceUrl);
	}
	
	@Test
	public void testFindAllWords_WhenWebContentContainsMultipleItems() throws Exception
	{
		URL resourceUrl = new URL(REPOSITORY_TEST_URL);
		
		// mock IHttpClient
		IHttpClient mockHttpClient = mock(IHttpClient.class);
		when(mockHttpClient.getContent(resourceUrl)).thenReturn("aaa" + WORD_DELIMITER + "bbb" + WORD_DELIMITER + "aba");
		
		WebWordRepository webWordRepository = new WebWordRepository(resourceUrl, mockHttpClient);
		
		List<Word> allWords = webWordRepository.findAllWords();
		
		// assert results
		assertThat(allWords, is(notNullValue()));
		assertThat(allWords.size(), is(equalTo(3)));
		
		Word word0 = allWords.get(0);
		Word word1 = allWords.get(1);
		Word word2 = allWords.get(2);
		
		assertThat(word0.toString(), is(equalTo("aaa")));
		assertThat(word1.toString(), is(equalTo("bbb")));
		assertThat(word2.toString(), is(equalTo("aba")));
		
		// verify interactions
		verify(mockHttpClient).getContent(resourceUrl);
	}
	
	@Test
	public void testFindAllWords_WhenWebContentContainsAposthrope() throws Exception
	{
		URL resourceUrl = new URL(REPOSITORY_TEST_URL);
		
		// mock IHttpClient
		IHttpClient mockHttpClient = mock(IHttpClient.class);
		when(mockHttpClient.getContent(resourceUrl)).thenReturn("aa'a" + WORD_DELIMITER + "bbb" + WORD_DELIMITER + "aba");
		
		WebWordRepository webWordRepository = new WebWordRepository(resourceUrl, mockHttpClient);
		
		List<Word> allWords = webWordRepository.findAllWords();
		
		// assert results
		assertThat(allWords, is(notNullValue()));
		assertThat(allWords.size(), is(equalTo(3)));
		
		Word word0 = allWords.get(0);
		Word word1 = allWords.get(1);
		Word word2 = allWords.get(2);
		
		assertThat(word0.toString(), is(equalTo("aaa")));
		assertThat(word1.toString(), is(equalTo("bbb")));
		assertThat(word2.toString(), is(equalTo("aba")));
		
		// verify interactions
		verify(mockHttpClient).getContent(resourceUrl);
	}
	
	@Test
	public void testFindAllWords_WhenWebContentContainsEmptyLines() throws Exception
	{
		URL resourceUrl = new URL(REPOSITORY_TEST_URL);
		
		// mock IHttpClient
		IHttpClient mockHttpClient = mock(IHttpClient.class);
		when(mockHttpClient.getContent(resourceUrl)).thenReturn("aaa"+ WORD_DELIMITER + " " + WORD_DELIMITER + "bbb" + WORD_DELIMITER + "aba" + WORD_DELIMITER);
		
		WebWordRepository webWordRepository = new WebWordRepository(resourceUrl, mockHttpClient);
		
		List<Word> allWords = webWordRepository.findAllWords();
		
		// assert results
		assertThat(allWords, is(notNullValue()));
		assertThat(allWords.size(), is(equalTo(3)));
		
		Word word0 = allWords.get(0);
		Word word1 = allWords.get(1);
		Word word2 = allWords.get(2);
		
		assertThat(word0.toString(), is(equalTo("aaa")));
		assertThat(word1.toString(), is(equalTo("bbb")));
		assertThat(word2.toString(), is(equalTo("aba")));
		
		// verify interactions
		verify(mockHttpClient).getContent(resourceUrl);
	}
	
	@Test(expected=Exception.class)
	public void testFindAllWords_WhenWebContentContainsMultipleWordsInLines_InvalidData() throws Exception
	{
		URL resourceUrl = new URL(REPOSITORY_TEST_URL);
		
		// mock IHttpClient
		IHttpClient mockHttpClient = mock(IHttpClient.class);
		when(mockHttpClient.getContent(resourceUrl)).thenReturn("aaa"+ WORD_DELIMITER + "ddd eee fff" + WORD_DELIMITER + "bbb" + WORD_DELIMITER + "aba"+ WORD_DELIMITER);
		
		WebWordRepository webWordRepository = new WebWordRepository(resourceUrl, mockHttpClient);
		
		List<Word> allWords = webWordRepository.findAllWords();
		
		// assert results
		assertThat(allWords, is(notNullValue()));
		assertThat(allWords.size(), is(equalTo(3)));
		
		Word word0 = allWords.get(0);
		Word word1 = allWords.get(1);
		Word word2 = allWords.get(2);
		
		assertThat(word0.toString(), is(equalTo("aaa")));
		assertThat(word1.toString(), is(equalTo("bbb")));
		assertThat(word2.toString(), is(equalTo("aba")));
		
		// verify interactions
		verify(mockHttpClient).getContent(resourceUrl);
	}

	@Test
	public void testFindDistinctWords_WhenWebContentIsEmpty() throws Exception
	{
		URL resourceUrl = new URL(REPOSITORY_TEST_URL);
		
		// mock IHttpClient
		IHttpClient mockHttpClient = mock(IHttpClient.class);
		when(mockHttpClient.getContent(resourceUrl)).thenReturn("");
		
		WebWordRepository webWordRepository = new WebWordRepository(resourceUrl, mockHttpClient);
		
		Set<Word> distinctWords = webWordRepository.findDistinctWords();
		
		// assert results
		assertThat(distinctWords, is(nullValue()));
		
		// verify interactions
		verify(mockHttpClient).getContent(resourceUrl);
	}
	
	@Test
	public void testFindDistinctWords_WhenWebContentContainsSingleItem() throws Exception
	{
		URL resourceUrl = new URL(REPOSITORY_TEST_URL);
		
		// mock IHttpClient
		IHttpClient mockHttpClient = mock(IHttpClient.class);
		when(mockHttpClient.getContent(resourceUrl)).thenReturn("aaa");
		
		WebWordRepository webWordRepository = new WebWordRepository(resourceUrl, mockHttpClient);
		
		Set<Word> distinctWords = webWordRepository.findDistinctWords();
		
		assertThat(distinctWords, is(notNullValue()));
		assertThat(distinctWords.size(), is(equalTo(1)));
		
		Word word = new ArrayList<Word>(distinctWords).get(0);
		
		assertThat(word.toString(), is(equalTo("aaa")));
		
		// verify interactions
		verify(mockHttpClient).getContent(resourceUrl);
	}

	@Test
	public void testFindDistinctWords_WhenWebContentContainsMultipleItem_And_ContainsNoDuplicates() throws Exception
	{
		URL resourceUrl = new URL(REPOSITORY_TEST_URL);
		
		// mock IHttpClient
		IHttpClient mockHttpClient = mock(IHttpClient.class);
		when(mockHttpClient.getContent(resourceUrl)).thenReturn("aaa" + WORD_DELIMITER + "bbb" + WORD_DELIMITER + "aba");
		
		WebWordRepository webWordRepository = new WebWordRepository(resourceUrl, mockHttpClient);
		
		Set<Word> distinctWords = webWordRepository.findDistinctWords();
		
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
		
		// verify interactions
		verify(mockHttpClient).getContent(resourceUrl);
	}
	
	@Test
	public void testFindDistinctWords_WhenWebContentContainsMultipleItem_And_ContainsDuplicates() throws Exception
	{
		URL resourceUrl = new URL(REPOSITORY_TEST_URL);
		
		// mock IHttpClient
		IHttpClient mockHttpClient = mock(IHttpClient.class);
		when(mockHttpClient.getContent(resourceUrl)).thenReturn("aaa" + WORD_DELIMITER + "bbb" + WORD_DELIMITER + "aba" + WORD_DELIMITER + "aaa" + WORD_DELIMITER + "aba");
		
		WebWordRepository webWordRepository = new WebWordRepository(resourceUrl, mockHttpClient);
		
		Set<Word> distinctWords = webWordRepository.findDistinctWords();
		
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
		
		// verify interactions
		verify(mockHttpClient).getContent(resourceUrl);
	}
}
