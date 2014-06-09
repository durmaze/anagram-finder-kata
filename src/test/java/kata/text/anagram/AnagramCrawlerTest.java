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

public class AnagramCrawlerTest
{
	@Test
	public void testFindAllAnagrams_WhenThereAreAnagrams() throws Exception
	{
		HashSet<Word> distinctWords = new HashSet<Word>();
		distinctWords.add(new Word("dummy"));
		
		// mock IWordRepository
		IWordRepository mockWordRepository = mock(IWordRepository.class);
		when(mockWordRepository.findDistinctWords()).thenReturn(distinctWords);
		
		Word expectedAnagram0 = new Word("abc");
		Word expectedAnagram1 = new Word("cab");
		Word expectedAnagram2 = new Word("bac");

		List<List<Word>> listOfAnagrams = new ArrayList<List<Word>>();
		listOfAnagrams.add(Arrays.asList(expectedAnagram0, expectedAnagram1, expectedAnagram2));

		// mock IAnagramFinder
		IAnagramFinder mockAnagramFinder = mock(IAnagramFinder.class);
		when(mockAnagramFinder.findAllAnagrams(anyListOf(Word.class))).thenReturn(listOfAnagrams);

		IAnagramCrawler anagramCrawler = new AnagramCrawler(mockWordRepository, mockAnagramFinder);

		// test SUT
		List<List<Word>> allAnagrams = anagramCrawler.findAllAnagrams();
		
		// assert results
		assertThat(allAnagrams, is(notNullValue()));
		assertThat(allAnagrams.size(), is(equalTo(1)));
		
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
		verify(mockAnagramFinder).findAllAnagrams(anyListOf(Word.class));
	}
	
	@Test
	public void testFindAllAnagrams_WhenThereIsNoAnagram() throws Exception
	{
		HashSet<Word> distinctWords = new HashSet<Word>();
		distinctWords.add(new Word("dummy"));
		
		// mock IWordRepository
		IWordRepository mockWordRepository = mock(IWordRepository.class);
		when(mockWordRepository.findDistinctWords()).thenReturn(distinctWords);
		
		// mock IAnagramFinder
		IAnagramFinder mockAnagramFinder = mock(IAnagramFinder.class);
		when(mockAnagramFinder.findAllAnagrams(anyListOf(Word.class))).thenReturn(null);
		
		IAnagramCrawler anagramCrawler = new AnagramCrawler(mockWordRepository, mockAnagramFinder);

		// test SUT
		List<List<Word>> allAnagrams = anagramCrawler.findAllAnagrams();
		
		// assert results
		assertThat(allAnagrams, is(nullValue()));
		
		// verify interactions
		verify(mockWordRepository).findDistinctWords();
		verify(mockAnagramFinder).findAllAnagrams(anyListOf(Word.class));
	}
	
	@Test
	public void testFindAllAnagrams_WhenRepositoryIsEmpty() throws Exception
	{
		// mock IWordRepository
		IWordRepository mockWordRepository = mock(IWordRepository.class);
		when(mockWordRepository.findDistinctWords()).thenReturn(null);
		
		// mock IAnagramFinder
		IAnagramFinder mockAnagramFinder = mock(IAnagramFinder.class);
		
		IAnagramCrawler anagramCrawler = new AnagramCrawler(mockWordRepository, mockAnagramFinder);

		// test SUT
		List<List<Word>> allAnagrams = anagramCrawler.findAllAnagrams();
		
		// assert results
		assertThat(allAnagrams, is(nullValue()));
		
		// verify interactions
		verify(mockWordRepository).findDistinctWords();
		verify(mockAnagramFinder, never()).findAllAnagrams(anyListOf(Word.class));
	}
}
