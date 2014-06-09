package kata.text.anagram;

import java.util.List;

/**
 * 
 * IAnagramFinder finds the anagrams of a Word or a list of Words
 * 
 * @author Erkan Durmaz
 * @date 17 Mar 2013
 * @time 18:35:51
 *
 */
public interface IAnagramFinder
{
	/**
	 * Finds the anagrams of the searched Word
	 * 
	 * @param searchedWord word that we look for its anagrams
	 * @param words list of words
	 * @return returns the anagrams
	 */
	public List<Word> findAnagrams(Word searchedWord, List<Word> words);
	
	/**
	 * Finds all the anagram groups in the words list
	 * 
	 * @param words
	 * @return returns the list of anagrams
	 */
	public List<List<Word>> findAllAnagrams(List<Word> words);
}
