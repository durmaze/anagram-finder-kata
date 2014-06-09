package kata.text.anagram;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;


public class MultithreadedAnagramCrawlerTest
{
	@Test
	public void testFindAllAnagrams_WhenThereAreAnagrams_ReturnedFromDifferentAnagramFinders() throws Exception
	{
		HashSet<Word> distinctWords = new HashSet<Word>();
		distinctWords.add(new Word("dummyA"));
		distinctWords.add(new Word("dummyB"));
		distinctWords.add(new Word("dummyC"));
		distinctWords.add(new Word("dummyD"));
		distinctWords.add(new Word("dummyE"));
		
		// mock IWordRepository
		IWordRepository mockWordRepository = mock(IWordRepository.class);
		when(mockWordRepository.findDistinctWords()).thenReturn(distinctWords);
		
		Word expectedAnagram0 = new Word("abc");
		Word expectedAnagram1 = new Word("cab");
		Word expectedAnagram2 = new Word("bac");

		List<List<Word>> listOfAnagrams0 = new ArrayList<List<Word>>();
		listOfAnagrams0.add(Arrays.asList(expectedAnagram0, expectedAnagram1, expectedAnagram2));

		Word expectedAnagram3 = new Word("xyz");
		Word expectedAnagram4 = new Word("zyx");
		
		List<List<Word>> listOfAnagrams1 = new ArrayList<List<Word>>();
		listOfAnagrams1.add(Arrays.asList(expectedAnagram3, expectedAnagram4));

		// mock the first IAnagramFinder
		IAnagramFinder mockAnagramFinder0 = mock(IAnagramFinder.class);
		when(mockAnagramFinder0.findAllAnagrams(anyListOf(Word.class))).thenReturn(listOfAnagrams0);
		
		// mock the second IAnagramFinder
		IAnagramFinder mockAnagramFinder1 = mock(IAnagramFinder.class);
		when(mockAnagramFinder1.findAllAnagrams(anyListOf(Word.class))).thenReturn(listOfAnagrams1);

		// mock IAnagramFinderFactory
		IAnagramFinderFactory mockAnagramFinderFactory = mock(IAnagramFinderFactory.class);
		when(mockAnagramFinderFactory.createAnagramFinder(anyListOf(Word.class))).thenReturn(mockAnagramFinder0).thenReturn(mockAnagramFinder1);

		IAnagramCrawler anagramCrawler = new MultithreadedAnagramCrawler(mockWordRepository, mockAnagramFinderFactory, 2);

		// test SUT
		List<List<Word>> allAnagrams = anagramCrawler.findAllAnagrams();
		
		// assert results
		assertThat(allAnagrams, is(notNullValue()));
		assertThat(allAnagrams.size(), is(equalTo(2)));
		
		// first list of anagrams
		List<Word> anagrams0 = allAnagrams.get(0);
		
		assertThat(anagrams0, is(notNullValue()));
		assertThat(anagrams0.size(), is(equalTo(3)));
		
		Word actualAnagram0 = anagrams0.get(0);
		Word actualAnagram1 = anagrams0.get(1);
		Word actualAnagram2 = anagrams0.get(2);
		
		assertThat(actualAnagram0, is(equalTo(expectedAnagram0)));
		assertThat(actualAnagram1, is(equalTo(expectedAnagram1)));
		assertThat(actualAnagram2, is(equalTo(expectedAnagram2)));
		
		// second list of anagrams
		List<Word> anagrams1 = allAnagrams.get(1);
		
		assertThat(anagrams1, is(notNullValue()));
		assertThat(anagrams1.size(), is(equalTo(2)));
		
		Word actualAnagram3 = anagrams1.get(0);
		Word actualAnagram4 = anagrams1.get(1);
		
		assertThat(actualAnagram3, is(equalTo(expectedAnagram3)));
		assertThat(actualAnagram4, is(equalTo(expectedAnagram4)));
		
		// verify interactions
		verify(mockWordRepository).findDistinctWords();
		verify(mockAnagramFinderFactory, times(2)).createAnagramFinder(anyListOf(Word.class));
		verify(mockAnagramFinder0).findAllAnagrams(anyListOf(Word.class));
		verify(mockAnagramFinder1).findAllAnagrams(anyListOf(Word.class));
	}
	
	@Test
	public void testFindAllAnagrams_WhenThereAreAnagrams_ReturnedFromSingleAnagramFinder() throws Exception
	{
//		IWordRepository wordRepository = new WebWordRepository(new URL(REPOSITORY_TEST_URL), new SimpleHttpClient(3000, 3000));
//		IWordRepository wordRepository = new FileWordRepository(new File(REPOSITORY_TEST_FILE));
		
		HashSet<Word> distinctWords = new HashSet<Word>();
		distinctWords.add(new Word("dummyA"));
		distinctWords.add(new Word("dummyB"));
		distinctWords.add(new Word("dummyC"));
		distinctWords.add(new Word("dummyD"));
		distinctWords.add(new Word("dummyE"));
		
		// mock IWordRepository
		IWordRepository mockWordRepository = mock(IWordRepository.class);
		when(mockWordRepository.findDistinctWords()).thenReturn(distinctWords);
		
		Word expectedAnagram0 = new Word("abc");
		Word expectedAnagram1 = new Word("cab");
		Word expectedAnagram2 = new Word("bac");
		
		List<List<Word>> listOfAnagrams0 = new ArrayList<List<Word>>();
		listOfAnagrams0.add(Arrays.asList(expectedAnagram0, expectedAnagram1, expectedAnagram2));
		
		// mock the first IAnagramFinder
		IAnagramFinder mockAnagramFinder0 = mock(IAnagramFinder.class);
		when(mockAnagramFinder0.findAllAnagrams(anyListOf(Word.class))).thenReturn(listOfAnagrams0);
		
		// mock the second IAnagramFinder
		IAnagramFinder mockAnagramFinder1 = mock(IAnagramFinder.class);
		when(mockAnagramFinder1.findAllAnagrams(anyListOf(Word.class))).thenReturn(null);
		
		// mock IAnagramFinderFactory
		IAnagramFinderFactory mockAnagramFinderFactory = mock(IAnagramFinderFactory.class);
		when(mockAnagramFinderFactory.createAnagramFinder(anyListOf(Word.class))).thenReturn(mockAnagramFinder0).thenReturn(mockAnagramFinder1);
		
		IAnagramCrawler anagramCrawler = new MultithreadedAnagramCrawler(mockWordRepository, mockAnagramFinderFactory, 2);
		
		// test SUT
		List<List<Word>> allAnagrams = anagramCrawler.findAllAnagrams();
		
		// assert results
		assertThat(allAnagrams, is(notNullValue()));
		assertThat(allAnagrams.size(), is(equalTo(1)));
		
		// first list of anagrams
		List<Word> anagrams0 = allAnagrams.get(0);
		
		assertThat(anagrams0, is(notNullValue()));
		assertThat(anagrams0.size(), is(equalTo(3)));
		
		Word actualAnagram0 = anagrams0.get(0);
		Word actualAnagram1 = anagrams0.get(1);
		Word actualAnagram2 = anagrams0.get(2);
		
		assertThat(actualAnagram0, is(equalTo(expectedAnagram0)));
		assertThat(actualAnagram1, is(equalTo(expectedAnagram1)));
		assertThat(actualAnagram2, is(equalTo(expectedAnagram2)));
		
		// verify interactions
		verify(mockWordRepository).findDistinctWords();
		verify(mockAnagramFinderFactory, times(2)).createAnagramFinder(anyListOf(Word.class));
		verify(mockAnagramFinder0).findAllAnagrams(anyListOf(Word.class));
		verify(mockAnagramFinder1).findAllAnagrams(anyListOf(Word.class));
	}
	
	@Test
	public void testFindAllAnagrams_WhenThereIsNoAnagram() throws Exception
	{
		HashSet<Word> distinctWords = new HashSet<Word>();
		distinctWords.add(new Word("dummyA"));
		distinctWords.add(new Word("dummyB"));
		distinctWords.add(new Word("dummyC"));
		distinctWords.add(new Word("dummyD"));
		distinctWords.add(new Word("dummyE"));
		
		// mock IWordRepository
		IWordRepository mockWordRepository = mock(IWordRepository.class);
		when(mockWordRepository.findDistinctWords()).thenReturn(distinctWords);
		
		// mock the first IAnagramFinder
		IAnagramFinder mockAnagramFinder0 = mock(IAnagramFinder.class);
		when(mockAnagramFinder0.findAllAnagrams(anyListOf(Word.class))).thenReturn(null);
		
		// mock the second IAnagramFinder
		IAnagramFinder mockAnagramFinder1 = mock(IAnagramFinder.class);
		when(mockAnagramFinder1.findAllAnagrams(anyListOf(Word.class))).thenReturn(null);
		
		// mock IAnagramFinderFactory
		IAnagramFinderFactory mockAnagramFinderFactory = mock(IAnagramFinderFactory.class);
		when(mockAnagramFinderFactory.createAnagramFinder(anyListOf(Word.class))).thenReturn(mockAnagramFinder0).thenReturn(mockAnagramFinder1);
		
		IAnagramCrawler anagramCrawler = new MultithreadedAnagramCrawler(mockWordRepository, mockAnagramFinderFactory, 2);
		
		// test SUT
		List<List<Word>> allAnagrams = anagramCrawler.findAllAnagrams();
		
		// assert results
		assertThat(allAnagrams, is(nullValue()));
		
		// verify interactions
		verify(mockWordRepository).findDistinctWords();
		verify(mockAnagramFinderFactory, times(2)).createAnagramFinder(anyListOf(Word.class));
		verify(mockAnagramFinder0).findAllAnagrams(anyListOf(Word.class));
		verify(mockAnagramFinder1).findAllAnagrams(anyListOf(Word.class));
	}
	
	@Test
	public void testFindAllAnagrams_WhenRepositoryIsEmpty() throws Exception
	{
		// mock IWordRepository
		IWordRepository mockWordRepository = mock(IWordRepository.class);
		when(mockWordRepository.findDistinctWords()).thenReturn(null);
		
		// mock IAnagramFinderFactory
		IAnagramFinderFactory mockAnagramFinderFactory = mock(IAnagramFinderFactory.class);
		when(mockAnagramFinderFactory.createAnagramFinder(anyListOf(Word.class))).thenReturn(null);
		
		IAnagramCrawler anagramCrawler = new MultithreadedAnagramCrawler(mockWordRepository, mockAnagramFinderFactory, 2);

		// test SUT
		List<List<Word>> allAnagrams = anagramCrawler.findAllAnagrams();
		
		// assert results
		assertThat(allAnagrams, is(nullValue()));
		
		// verify interactions
		verify(mockWordRepository).findDistinctWords();
		verify(mockAnagramFinderFactory, never()).createAnagramFinder(anyListOf(Word.class));
	}
}
