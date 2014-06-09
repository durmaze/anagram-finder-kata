package kata.text.utils;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class SimpleHttpClientTest
{
	private static final String REPOSITORY_TEST_URL = "http://www-01.sil.org/linguistics/wordlists/english/wordlist/wordsEn.txt";
	private static final String WORD_DELIMITER = "\n";
	
	@BeforeClass
	public static void oneTimeSetUp() throws Exception
	{
		HttpURLConnection connection = (HttpURLConnection) new URL(REPOSITORY_TEST_URL).openConnection();
		connection.setRequestMethod("GET");
		connection.setConnectTimeout(3000);
		
		connection.connect();
		
		assertThat(connection.getResponseCode(), is(equalTo(HttpURLConnection.HTTP_OK)));
	}
	
	@Test
	public void testGetContent() throws Exception
	{
		SimpleHttpClient httpClient = new SimpleHttpClient(3000, 3000);
		
		String content = httpClient.getContent(new URL(REPOSITORY_TEST_URL));
		
		assertThat(content, is(notNullValue()));
		
		if (!content.isEmpty())
		{
			List<String> wordList = CollectionUtils.createListFromDelimitedText(content, WORD_DELIMITER);
			
			assertThat(wordList, is(notNullValue()));
			assertThat(wordList.size(), is(greaterThan(0)));
		}
	}
}
