package kata.text.anagram;

/**
 * 
 * IAnagramChecker makes the actual anagram-check of 2 Words. AnagramCheckers can vary in implemetation based on space-effiency,   
 * CPU-effiency, language, etc.
 * 
 * @author Erkan Durmaz
 * @date 17 Mar 2013
 * @time 18:49:28
 *
 */
public interface IAnagramChecker
{
	/**
	 * Checks if word1 and word2 are anagrams
	 * 
	 * @param word1
	 * @param word2
	 * @return returns true if word1 and word2 are anagrams.
	 */
	public boolean checkWords(Word word1, Word word2);
}
