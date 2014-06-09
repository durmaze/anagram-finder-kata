package kata.text.anagram;

import kata.text.utils.ListSplitterTest;
import kata.text.utils.SimpleHttpClientTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses
(
		{ 	
			AnagramCrawlerTest.class,
			AnagramFinderTest.class, 
			FileWordRepositoryTest.class, 
			FrequencyBasedAnagramCheckerTest.class,
			MultithreadedAnagramCrawlerTest.class,
			SimpleAnagramCheckerTest.class, 
			WebWordRepositoryTest.class,
			WordTest.class,
			ListSplitterTest.class,
			SimpleHttpClientTest.class
		}
)
public class AnagramAllTest
{

}
