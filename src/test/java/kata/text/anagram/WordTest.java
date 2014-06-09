package kata.text.anagram;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class WordTest
{

	@Test
	public void testWord_WhenArgumentIsAWordString()
	{
		Word word = new Word("hello");
		
		assertThat(word, is(notNullValue()));
	}
	
	@Test
	public void testWord_WhenArgumentIsAWordString_ButContainsLeadingWhitespaces()
	{
		Word word = new Word("    hello");
		
		assertThat(word, is(notNullValue()));
	}
	
	@Test
	public void testWord_WhenArgumentIsAWordString_ButContainsTrailingWhitespaces()
	{
		Word word = new Word("hello   ");
		
		assertThat(word, is(notNullValue()));
	}

	@Test(expected=NullPointerException.class)
	public void testWord_WhenArgumentIsNull()
	{
		new Word(null);
	}
	
	@Test(expected=Exception.class)
	public void testWord_WhenArgumentIsEmpty()
	{
		new Word("");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testWord_WhenArgumentIsWhitespace()
	{
		new Word("      ");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testWord_WhenArgumentContainNumericCharacters()
	{
		new Word("Hello123");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testWord_WhenArgumentContainNonAlphaCharacters()
	{
		new Word("Hello'<<)");
	}
	
	@Test
	public void testEqualsObject_WhenTwoWordsAreTheSameWordInstance()
	{
		Word word1 = new Word("hello");
		Word word2 = word1;
		
		boolean isEqual = word1.equals(word2);
		
		assertThat(isEqual, is(true));
	}
	
	@Test
	public void testEqualsObject_WhenTwoWordsContainTheSameString()
	{
		Word word1 = new Word("hello");
		Word word2 = new Word("hello");
		
		boolean isEqual = word1.equals(word2);
		
		assertThat(isEqual, is(true));
	}
	
	@Test
	public void testEqualsObject_WhenTwoWordsContainDifferentStrings()
	{
		Word word1 = new Word("hello");
		Word word2 = new Word("adae");
		
		boolean isEqual = word1.equals(word2);
		
		assertThat(isEqual, is(false));
	}
	
	@Test
	public void testEqualsObject_WhenWord2IsNull()
	{
		Word word1 = new Word("hello");
		Word word2 = null;
		
		boolean isEqual = word1.equals(word2);
		
		assertThat(isEqual, is(false));
	}
	
	@Test
	public void testHashCode_WhenTwoWordsAreTheSameWordInstance()
	{
		Word word1 = new Word("hello");
		Word word2 = word1;
		
		boolean isEqual = (word1.hashCode() == word2.hashCode());
		
		assertThat(isEqual, is(true));
	}
	
	@Test
	public void testHashCode_WhenTwoWordsContainTheSameString()
	{
		Word word1 = new Word("hello");
		Word word2 = new Word("hello");
		
		boolean isEqual = (word1.hashCode() == word2.hashCode());
		
		assertThat(isEqual, is(true));
	}
	
	@Test
	public void testHashCode_WhenTwoWordsContainDifferentStrings()
	{
		Word word1 = new Word("hello");
		Word word2 = new Word("adae");
		
		boolean isEqual = (word1.hashCode() == word2.hashCode());
		
		assertThat(isEqual, is(false));
	}

	@Test
	public void testLength()
	{
		Word word = new Word("HelloWorld");

		assertThat(word.length(), is(equalTo("HelloWorld".length())));
	}

	@Test
	public void testToLowerCase()
	{
		Word word = new Word("HelloWorld");

		assertThat(word.toLowerCase(), is(equalTo(new Word("helloworld"))));
	}

	@Test
	public void testToUpperCase()
	{
		Word word = new Word("HelloWorld");

		assertThat(word.toUpperCase(), is(equalTo(new Word("HELLOWORLD"))));
	}

	@Test
	public void testToCharArray()
	{
		Word word = new Word("HelloWorld");

		assertThat(word.toCharArray(), is(equalTo("HelloWorld".toCharArray())));
	}

	@Test
	public void testToString()
	{
		Word word = new Word("HelloWorld");
		
		assertThat(word.toString(), is(equalTo("HelloWorld")));
	}

}
