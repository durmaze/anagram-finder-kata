package kata.text.anagram;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import kata.text.concurrency.KataThreadFactory;
import kata.text.exception.KataException;
import kata.text.utils.ListSplitter;

/**
 * 
 * MultithreadedAnagramCrawler is a multi-threaded implementation of IAnagramCrawler. For large Word repositories, it could be 
 * reasonable to crawl the repository with multiple worker threads.
 * 
 * MultithreadedAnagramCrawler uses a divide-and-conquer algorithm, which partitions words in the repository to chunks, and
 * gives each chunk to a separate IAnagramFinder, which runs in a thread of its own. Results are then merged after all the finders
 * are finished.
 * 
 * @author Erkan Durmaz
 * @date 17 Mar 2013
 * @time 18:54:30
 *
 */
public class MultithreadedAnagramCrawler implements IAnagramCrawler
{
	// dependencies & invariants
	private final IWordRepository wordRepository;
	private final IAnagramFinderFactory anagramFinderFactory;
	private final int threadPoolSize;
	private final ExecutorService crawlerExecutor;

	public MultithreadedAnagramCrawler(IWordRepository wordRepository, IAnagramFinderFactory anagramFinderFactory, int threadPoolSize)
	{
		// inject dependencies
		if (wordRepository == null)
		{
			throw new NullPointerException("wordRepository is null");
		}

		if (anagramFinderFactory == null)
		{
			throw new NullPointerException("anagramFinderFactory is null");
		}

		if (threadPoolSize < 1)
		{
			throw new IllegalArgumentException("threadPoolSize must be at least 1");
		}

		this.wordRepository = wordRepository;
		this.anagramFinderFactory = anagramFinderFactory;
		this.threadPoolSize = threadPoolSize;

		// create thread pool
		this.crawlerExecutor = Executors.newFixedThreadPool(this.threadPoolSize, new KataThreadFactory("CrawlerWorkerPool"));
	}

	@Override
	public List<List<Word>> findAllAnagrams()
	{
		try
		{
			// fetch Words from Repository
			Set<Word> words = this.wordRepository.findDistinctWords();

			// list of all the anagrams that we gonna fill
			List<List<Word>> listOfAllAnagrams = new ArrayList<List<Word>>();

			if (words != null && !words.isEmpty())
			{
				// wrap AnagramFinders to callables
				List<Callable<List<List<Word>>>> workerCallables = new ArrayList<Callable<List<List<Word>>>>();

				// partition words into chunks, and give each chunk to a separate IAnagramFinder (in a worker thread)
				ListSplitter<Word> listSplitter = new ListSplitter<Word>(new ArrayList<Word>(words));

				for (final List<Word> wordsChunk : listSplitter.splitByCount(this.threadPoolSize))
				{
					final IAnagramFinder anagramFinder = this.anagramFinderFactory.createAnagramFinder(wordsChunk);

					Callable<List<List<Word>>> callable = new Callable<List<List<Word>>>()
					{
						@Override
						public List<List<Word>> call() throws Exception
						{
							return anagramFinder.findAllAnagrams(wordsChunk);
						}
					};

					workerCallables.add(callable);
				}

				// find anagrams
				List<Future<List<List<Word>>>> futures = this.crawlerExecutor.invokeAll(workerCallables);

				// merge the list of anagrams collected by IAnagramFinders
				for (Future<List<List<Word>>> future : futures)
				{
					// list of the anagrams from a single IAnagramFinder
					List<List<Word>> listOfAnagrams = future.get();
					
					if (listOfAnagrams != null)
					{
						for (List<Word> anagrams : listOfAnagrams)
						{
							// only add if we have an anagram for a Word (i.e. other than itself)
							if (anagrams.size() > 1)
							{
								listOfAllAnagrams.add(anagrams);
							}
						}
					}
				}
			}
			
			return !listOfAllAnagrams.isEmpty() ? listOfAllAnagrams : null;
		}
		catch (Exception e)
		{
			throw new KataException(e);
		}
	}

	@Override
	public void close()
	{
		this.crawlerExecutor.shutdown();
	}
}
