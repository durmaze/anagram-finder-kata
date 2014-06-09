package kata.text.anagram.main;

import java.io.File;
import java.net.URL;
import java.util.List;

import kata.text.anagram.AnagramFinderFactory;
import kata.text.anagram.FileWordRepository;
import kata.text.anagram.FrequencyBasedAnagramCheckerFactory;
import kata.text.anagram.IAnagramCheckerFactory;
import kata.text.anagram.IAnagramCrawler;
import kata.text.anagram.IAnagramFinderFactory;
import kata.text.anagram.IWordRepository;
import kata.text.anagram.MultithreadedAnagramCrawler;
import kata.text.anagram.WebWordRepository;
import kata.text.anagram.Word;
import kata.text.anagram.logging.LoggingAnagramCrawler;
import kata.text.logging.ILogManager;
import kata.text.logging.Log4jLogManager;
import kata.text.utils.SimpleHttpClient;
import kata.text.utils.TimeSpan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * AnagramFinderApp finds anagrams of words. Words can be supplied by a file location or a URL
 * 
 * @author Erkan Durmaz
 * @date 17 Mar 2013
 * @time 17:30:01
 *
 */
public class AnagramFinderApp
{
	// constants
	private static final String LOG_CONFIG_FILENAME = "log4j.xml";
	private static final String LOG_DIRECTORY = "logs";
	private static final String LOG_FILENAME = "AnagramFinder";

	// logger
	private static Logger logger;

	public static void main(String[] args)
	{
		ILogManager logManager = createAndConfigureLogManager();

		// init logger
		logger = LoggerFactory.getLogger(AnagramFinderApp.class);

		// validate command-line arguments
		if (args == null || args.length != 2)
		{
			printUsageAndExit();
		}

		String repositoryType = args[0];
		String location = args[1];

		if (!isSupportedRepositoryType(repositoryType))
		{
			printUsageAndExit();
		}

		try
		{
			/*
			 * COMPOSITION ROOT
			 * 
			 * Create entire object graph here
			 */

			// create IWordRepository
			IWordRepository wordRepository = (repositoryType.equals("-file"))
												? new FileWordRepository(new File(location))
												: new WebWordRepository(new URL(location), new SimpleHttpClient(4000, 4000));

			// create IAnagramCheckerFactory
			IAnagramCheckerFactory anagramCheckerFactory = new FrequencyBasedAnagramCheckerFactory(); // new AnagramCheckerFactory();
			
			// create IAnagramFinderFactory
			IAnagramFinderFactory anagramFinderFactory = new AnagramFinderFactory(anagramCheckerFactory);
			
			// create IAnagramCrawler
			IAnagramCrawler anagramCrawler = new LoggingAnagramCrawler(new MultithreadedAnagramCrawler(wordRepository, anagramFinderFactory, 20));
			
			logger.info("AnagramFinderApp is started with arguments => RepositoryType: " + repositoryType + ", Location: " + location);
			
			// run IAnagramCrawler
			List<List<Word>> listOfAnagrams = anagramCrawler.findAllAnagrams();
			
			// print results
			for (List<Word> anagrams : listOfAnagrams)
			{
				logger.info(anagrams.toString());
			}
		}
		catch (Exception ex)
		{
			logger.error("Unexpected exception occurred in AnagramFinderApp", ex);
		}
		catch (Error err)
		{
			logger.error("Unexpected error occurred in AnagramFinderApp", err);
		}

		logger.info("AnagramFinderApp is exited.");

		// shutdown underlying logging framework
		logManager.shutdown();
		
		// exit explicitly in order to get rid of any hanging threads, if there is any (preventing shutdown)
		System.exit(0);
	}

	private static void printUsageAndExit()
	{
		logger.error("Usage: AnagramFinderApp [-file|-url] [location]");
		logger.error("\t example 1: AnagramFinderApp -file C:\\words.txt");
		logger.error("\t example 2: AnagramFinderApp -url http://www-01.sil.org/linguistics/wordlists/english/wordlist/wordsEn.txt");

		System.exit(1);
	}

	private static boolean isSupportedRepositoryType(String repositoryType)
	{
		return (repositoryType.equals("-file") || repositoryType.equals("-url"));
	}

	private static ILogManager createAndConfigureLogManager()
	{
		// configure logging system
		boolean isVerboseMode = System.getProperty("log.verboseMode") != null && System.getProperty("log.verboseMode").equals("true");
		boolean shouldWatch = System.getProperty("log.shouldWatch") != null && System.getProperty("log.shouldWatch").equals("true");

		ILogManager logManager = new Log4jLogManager(isVerboseMode);

		try
		{
			if (shouldWatch)
			{
				TimeSpan watchPeriod = ILogManager.DEFAULT_WATCH_PERIOD;

				try
				{
					watchPeriod = TimeSpan.fromSeconds(Integer.parseInt(System.getProperty("log.watchPeriod")));
				}
				catch (Exception e)
				{
					System.out.println("WatchPeriod for logging configuration is not found or cannot be read. Using default watch period: " + ILogManager.DEFAULT_WATCH_PERIOD.toMilliSeconds() + " ms");
				}

				logManager.configureAndWatch(LOG_CONFIG_FILENAME, LOG_DIRECTORY, LOG_FILENAME, watchPeriod);
			}
			else
			{
				logManager.configure(LOG_CONFIG_FILENAME, LOG_DIRECTORY, LOG_FILENAME);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}

		return logManager;
	}
}
