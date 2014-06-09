package kata.text.anagram;

import java.util.List;
import java.util.Set;

/**
 * 
 * IWordRepository is a Repository for Words. 
 * 
 * In the future, different Words can be fetched based on their attributes (e.g. based on their length or their prefix)
 * 
 * @author Erkan Durmaz
 * @date 17 Mar 2013
 * @time 18:37:50
 *
 */
public interface IWordRepository
{
	/**
	 * Finds all the Words in the repository
	 * @return words
	 */
	public List<Word> findAllWords();
	
	/**
	 * Finds all the distinct Words (i.e. no duplicates allowed) in the repository
	 * @return distinct words
	 */
	public Set<Word> findDistinctWords();
}
