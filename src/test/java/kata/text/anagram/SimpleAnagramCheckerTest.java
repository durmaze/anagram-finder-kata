package kata.text.anagram;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class SimpleAnagramCheckerTest
{
	@Test(expected=NullPointerException.class)
	public void testCheckWords_WhenWord1IsNull()
	{
		SimpleAnagramChecker anagramChecker = new SimpleAnagramChecker();
		
		anagramChecker.checkWords(null, new Word("Hello"));
	}

	@Test(expected=NullPointerException.class)
	public void testCheckWords_WhenWord2IsNull()
	{
		SimpleAnagramChecker anagramChecker = new SimpleAnagramChecker();
		
		anagramChecker.checkWords(new Word("Hello"), null);
	}

	@Test
	public void testCheckWords_WhenWordsLengthIsNotEqual()
	{
		SimpleAnagramChecker anagramChecker = new SimpleAnagramChecker();
		
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
		SimpleAnagramChecker anagramChecker = new SimpleAnagramChecker();
		
		boolean isAnagrams = anagramChecker.checkWords(new Word("spar"), new Word("rasp"));
		
		assertThat(isAnagrams, is(equalTo(true)));
	}
	
	@Test
	public void testCheckWords_WhenWordsAreAnagrams_And_WordsDifferInLetterCase()
	{
		SimpleAnagramChecker anagramChecker = new SimpleAnagramChecker();
		
		boolean isAnagrams = anagramChecker.checkWords(new Word("Spar"), new Word("rAsp"));
		
		assertThat(isAnagrams, is(equalTo(true)));
	}
	
	@Test
	public void testCheckWords_WhenQuiteLongWordsAreAnagrams()
	{
		SimpleAnagramChecker anagramChecker = new SimpleAnagramChecker();
		
		boolean isAnagrams = anagramChecker.checkWords(new Word("orchestra"), new Word("carthorse"));
		
		assertThat(isAnagrams, is(equalTo(true)));
	}
	
	@Test
	public void testCheckWords_WhenWordsAreNotAnagrams()
	{
		SimpleAnagramChecker anagramChecker = new SimpleAnagramChecker();
		
		boolean isAnagrams = anagramChecker.checkWords(new Word("spar"), new Word("dude"));
		
		assertThat(isAnagrams, is(equalTo(false)));
	}
}
