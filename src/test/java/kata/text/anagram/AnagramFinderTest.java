package kata.text.anagram;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AnagramFinderTest
{
	// dependency
	private final IAnagramChecker anagramChecker;

	@Parameters
	public static Collection<Object[]> generateTestDataForDifferentAnagramCheckers()
	{
		return Arrays.asList(new Object[][] 
				{ 
					{ new SimpleAnagramChecker() }, 
					{ new FrequencyBasedAnagramChecker() } 
				});
	}

	public AnagramFinderTest(IAnagramChecker anagramChecker)
	{
		// inject dependency
		if (anagramChecker == null)
		{
			throw new NullPointerException("anagramChecker is null");
		}

		this.anagramChecker = anagramChecker;
	}
	
	@Test(expected=NullPointerException.class)
	public void testFindAnagrams_WhenWordIsNull()
	{
		IAnagramFinder anagramFinder = new AnagramFinder(this.anagramChecker);
		
		anagramFinder.findAnagrams(null, new ArrayList<Word>());
	}
	
	@Test(expected=NullPointerException.class)
	public void testFindAnagrams_WhenWordsIsNull()
	{
		IAnagramFinder anagramFinder = new AnagramFinder(this.anagramChecker);
		
		anagramFinder.findAnagrams(new Word("aaa"), null);
	}
	
	@Test(expected=NullPointerException.class)
	public void testFindAnagrams_WhenWordsIsEmpty()
	{
		IAnagramFinder anagramFinder = new AnagramFinder(this.anagramChecker);
		
		anagramFinder.findAnagrams(new Word("aaa"), new ArrayList<Word>());
	}

	@Test
	public void testFindAnagrams_WhenWordsHasOneAnagram_AndWordsContainOneWord()
	{
		IAnagramFinder anagramFinder = new AnagramFinder(this.anagramChecker);
		
		List<Word> words = new ArrayList<Word>();
		
		words.add(new Word("rasp"));
		
		List<Word> anagrams = anagramFinder.findAnagrams(new Word("spar"), words);
		
		assertThat(anagrams, is(notNullValue()));
		assertThat(anagrams.size(), is(equalTo(1)));
		
		Word anagram0 = anagrams.get(0);
		
		assertThat(anagram0, is(equalTo(new Word("rasp"))));
	}
	
	@Test
	public void testFindAnagrams_WhenWordsHasOneAnagram_AndWordsContainMoreThanOneWord()
	{
		IAnagramFinder anagramFinder = new AnagramFinder(this.anagramChecker);
		
		List<Word> words = new ArrayList<Word>();
		
		words.add(new Word("rasp"));
		words.add(new Word("acd"));
		words.add(new Word("raspas"));
		words.add(new Word("barasp"));
		words.add(new Word("xyx"));
		
		List<Word> anagrams = anagramFinder.findAnagrams(new Word("spar"), words);
		
		assertThat(anagrams, is(notNullValue()));
		assertThat(anagrams.size(), is(equalTo(1)));
		
		Word anagram0 = anagrams.get(0);
		
		assertThat(anagram0, is(equalTo(new Word("rasp"))));
	}
	
	@Test
	public void testFindAnagrams_WhenWordsContainMoreThanOneAnagram()
	{
		IAnagramFinder anagramFinder = new AnagramFinder(this.anagramChecker);
		
		List<Word> words = new ArrayList<Word>();
		
		words.add(new Word("rasp"));
		words.add(new Word("acd"));
		words.add(new Word("aspr"));
		words.add(new Word("barasp"));
		words.add(new Word("xyx"));
		words.add(new Word("pasr"));
		words.add(new Word("xyx"));
		
		List<Word> anagrams = anagramFinder.findAnagrams(new Word("spar"), words);
		
		assertThat(anagrams, is(notNullValue()));
		assertThat(anagrams.size(), is(equalTo(3)));
		
		Word anagram0 = anagrams.get(0);
		Word anagram1 = anagrams.get(1);
		Word anagram2 = anagrams.get(2);
		
		assertThat(anagram0, isOneOf(new Word("rasp"), new Word("aspr"), new Word("pasr")));
		assertThat(anagram1, isOneOf(new Word("rasp"), new Word("aspr"), new Word("pasr")));
		assertThat(anagram2, isOneOf(new Word("rasp"), new Word("aspr"), new Word("pasr")));
	}
}
