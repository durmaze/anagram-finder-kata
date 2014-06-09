package kata.text.anagram;

import java.util.Arrays;

/**
 * 
 * SimpleAnagramChecker checks whether 2 words are anagrams based on their character order.
 * 
 * If two words are anagrams, their character order has to be identical. 
 * 
 * @author Erkan Durmaz
 * @date 16 Mar 2013
 * @time 14:45:23
 *
 */
public class SimpleAnagramChecker implements IAnagramChecker
{
	@Override
	public boolean checkWords(Word word1, Word word2)
	{
		if (word1 == null)
		{
			throw new NullPointerException("word1 is null");
		}
		
		if (word2 == null)
		{
			throw new NullPointerException("word2 is null");
		}
		
		// early wake-up
		if (word1.length() != word2.length())
		{
			return false;
		}
		
		// case doesn't matter (since two words are are considered anagrams based on their letter set)
		char[] charArray1 = word1.toLowerCase().toCharArray();
		char[] charArray2 = word2.toLowerCase().toCharArray();
		
		// sort characters in alphabetical order
		Arrays.sort(charArray1);
		Arrays.sort(charArray2);
		
		// compare sorted char arrays
		return Arrays.equals(charArray1, charArray2);
	}
}
