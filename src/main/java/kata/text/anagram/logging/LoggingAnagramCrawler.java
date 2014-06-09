package kata.text.anagram.logging;

import java.util.List;

import kata.text.anagram.IAnagramCrawler;
import kata.text.anagram.Word;
import kata.text.exception.KataException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingAnagramCrawler implements IAnagramCrawler
{
	// logger
	private final Logger logger = LoggerFactory.getLogger(LoggingAnagramCrawler.class);

	// dependency
	private final IAnagramCrawler underlyingAnagramCrawler;

	public LoggingAnagramCrawler(IAnagramCrawler underlyingAnagramCrawler)
	{
		// inject dependency
		if (underlyingAnagramCrawler == null)
		{
			throw new NullPointerException("underlyingAnagramCrawler is null");
		}

		this.underlyingAnagramCrawler = underlyingAnagramCrawler;
	}
	
	@Override
	public List<List<Word>> findAllAnagrams()
	{
		try 
		{
			this.logger.debug("findAllAnagrams is being called.");
			
			List<List<Word>> allAnagrams = this.underlyingAnagramCrawler.findAllAnagrams();
			
			this.logger.debug("findAllAnagrams is successfully called.");

			return allAnagrams;
		} 
		catch (KataException e) 
		{	
			this.logger.warn("Exception occurred in findAllAnagrams", e);
			throw e;
		}
	}

	@Override
	public void close()
	{
		try 
		{
			this.logger.debug("close is being called.");
			
			this.underlyingAnagramCrawler.close();
			
			this.logger.debug("close is successfully called.");
		} 
		catch (KataException e) 
		{	
			this.logger.warn("Exception occurred in close", e);
			throw e;
		}
	}
}
