package kata.text.anagram;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import kata.text.exception.KataException;

/**
 * 
 * AnagramCrawler 
 * 
 * Crawls the WordRepository for the anagram of the words. AnagramCrawler uses a single IAnagramFinder
 * to find the anagrams. This class could be poor in performance for large repositories.
 * 
 * @author Erkan Durmaz
 * @date 17 Mar 2013
 * @time 18:05:35
 *
 */
public class AnagramCrawler implements IAnagramCrawler
{
	// dependencies
	private final IWordRepository wordRepository;
	private final IAnagramFinder anagramFinder;

	public AnagramCrawler(IWordRepository wordRepository, IAnagramFinder anagramFinder)
	{
		// inject dependencies
		if (wordRepository == null)
		{
			throw new NullPointerException("wordRepository is null");
		}

		if (anagramFinder == null)
		{
			throw new NullPointerException("anagramFinder is null");
		}

		this.wordRepository = wordRepository;
		this.anagramFinder = anagramFinder;
	}

	public List<List<Word>> findAllAnagrams()
	{
		try
		{
			// fetch Words from Repository
			Set<Word> words = this.wordRepository.findDistinctWords();

			if (words != null && !words.isEmpty())
			{
				return this.anagramFinder.findAllAnagrams(new ArrayList<Word>(words));
			}
			
			return null;
		}
		catch (Exception e)
		{
			throw new KataException(e);
		}
	}

	@Override
	public void close()
	{
		// nothing to dispose
	}
}
