package kata.text.anagram;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class FrequencyBasedAnagramCheckerTest
{
	@Test(expected=NullPointerException.class)
	public void testCheckWords_WhenWord1IsNull()
	{
		FrequencyBasedAnagramChecker anagramChecker = new FrequencyBasedAnagramChecker();
		
		anagramChecker.checkWords(null, new Word("Hello"));
	}

	@Test(expected=NullPointerException.class)
	public void testCheckWords_WhenWord2IsNull()
	{
		FrequencyBasedAnagramChecker anagramChecker = new FrequencyBasedAnagramChecker();
		
		anagramChecker.checkWords(new Word("Hello"), null);
	}

	@Test
	public void testCheckWords_WhenWordsLengthIsNotEqual()
	{
		FrequencyBasedAnagramChecker anagramChecker = new FrequencyBasedAnagramChecker();
		
		boolean isAnagrams = anagramChecker.checkWords(new Word("Hello"), new Word("HelloA"));
		
		assertThat(isAnagrams, is(equalTo(false)));
	}
	
	@Test
	public void testCheckWords_WhenWordsAreSame()
	{
		SimpleAnagramChecker anagramChecker = new SimpleAnagramChecker();
		
		boolean isAnagrams = anagramChecker.checkWords(new Word("spar"), new Word("spar"));
		
		assertThat(isAnagrams, is(equalTo(true)));
	}
	
	@Test
	public void testCheckWords_WhenWordsAreAnagrams()
	{
		FrequencyBasedAnagramChecker anagramChecker = new FrequencyBasedAnagramChecker();
		
		boolean isAnagrams = anagramChecker.checkWords(new Word("spar"), new Word("rasp"));
		
		assertThat(isAnagrams, is(equalTo(true)));
	}
	
	@Test
	public void testCheckWords_WhenWordsAreAnagrams_And_WordsDifferInLetterCase()
	{
		FrequencyBasedAnagramChecker anagramChecker = new FrequencyBasedAnagramChecker();
		
		boolean isAnagrams = anagramChecker.checkWords(new Word("Spar"), new Word("rAsp"));
		
		assertThat(isAnagrams, is(equalTo(true)));
	}
	
	@Test
	public void testCheckWords_WhenQuiteLongWordsAreAnagrams()
	{
		FrequencyBasedAnagramChecker anagramChecker = new FrequencyBasedAnagramChecker();
		
		boolean isAnagrams = anagramChecker.checkWords(new Word("orchestra"), new Word("carthorse"));
		
		assertThat(isAnagrams, is(equalTo(true)));
	}
	
	@Test
	public void testCheckWords_WhenWordsAreNotAnagrams()
	{
		FrequencyBasedAnagramChecker anagramChecker = new FrequencyBasedAnagramChecker();
		
		boolean isAnagrams = anagramChecker.checkWords(new Word("spar"), new Word("dude"));
		
		assertThat(isAnagrams, is(equalTo(false)));
	}
	
	@Test
	public void testCheckWords_WhenLettersAreAtTheEdge()
	{
		FrequencyBasedAnagramChecker anagramChecker = new FrequencyBasedAnagramChecker();
		
		boolean isAnagrams = anagramChecker.checkWords(new Word("abc"), new Word("xyz"));
		
		assertThat(isAnagrams, is(equalTo(false)));
	}
}
