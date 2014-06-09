package kata.text.anagram;

/**
 * 
 * FrequencyBasedAnagramChecker is a low complexity and space-efficient anagram checker.
 * 
 * FrequencyBasedAnagramChecker keeps a language-specific letter list to count the letters used in the Words.
 * If two words has the exact letter count, then they must be anagrams. In order to be space-efficient, same letter list
 * is used to count the letters of Word1 and Word2. Letter count is incremented for Word1 and decremented for Word2, 
 * expecting they equal out their counts at the end (i.e. having zero letter count). 
 * 
 * 
 * @author Erkan Durmaz
 * @date 17 Mar 2013
 * @time 18:42:55
 *
 */
public class FrequencyBasedAnagramChecker implements IAnagramChecker
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
		
		// assuming we process only English words 
		int[] letterCounts = new int[26];

		// increment for Word1
		for (char c1 : charArray1)
		{
			letterCounts[getLetterIndex(c1)]++;
		}

		// decrement for Word2
		for (char c2 : charArray2)
		{
			letterCounts[getLetterIndex(c2)]--;
		}
		
		for (int letterCount : letterCounts)
		{
			if (letterCount != 0)
			{
				return false;
			}
		}
		
		return true;
	}

	private int getLetterIndex(char ch)
	{
		return (int) ch - (int) 'a';
	}
}
