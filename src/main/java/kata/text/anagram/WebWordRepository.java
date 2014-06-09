package kata.text.anagram;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kata.text.anagram.exception.AnagramExceptionCodes;
import kata.text.exception.KataException;
import kata.text.utils.CollectionUtils;
import kata.text.utils.IHttpClient;

/**
 * 
 * WebWordRepository is an implementation of IWordRepository, which expects its Words from a given URL
 * 
 * Words stored at the URL must be delimited by new line.
 * 
 * @author Erkan Durmaz
 * @date 17 Mar 2013
 * @time 18:40:52
 *
 */
public class WebWordRepository implements IWordRepository
{
	// constants
	private static final String NEW_LINE_REGEX = "\n";
	
	// dependencies
	private final URL resourceUrl;
	private final IHttpClient httpClient;

	public WebWordRepository(URL resourceUrl, IHttpClient httpClient)
	{
		// inject dependencies
		if (resourceUrl == null)
		{
			throw new NullPointerException("resourceUrl is null");
		}

		if (httpClient == null)
		{
			throw new NullPointerException("httpClient is null");
		}

		this.resourceUrl = resourceUrl;
		this.httpClient = httpClient;
	}
	
	@Override
	public List<Word> findAllWords()
	{
		// get content from URL
		String content = this.httpClient.getContent(this.resourceUrl);
		
		try 
		{
			// if we have content, parse the content and return a Word list
			if (content != null && !content.isEmpty())
			{
				// escape apostrophes
				content = content.replaceAll("'", "");
				
				List<Word> words = new ArrayList<Word>();
				
				for (String aString : CollectionUtils.createListFromDelimitedText(content, NEW_LINE_REGEX))
				{
					if (!aString.trim().isEmpty())
					{
						words.add(new Word(aString));
					}
				} 
				
				return words;
			}
			
			return null;
		} 
		catch (Exception e) 
		{	
			throw new KataException(e, AnagramExceptionCodes.WORDREPOSITORY_INVALID_CONTENT);
		}
	}

	@Override
	public Set<Word> findDistinctWords()
	{
		List<Word> allWords = findAllWords();
		
		return (allWords != null) ? new HashSet<Word>(allWords) : null;
	}
}
