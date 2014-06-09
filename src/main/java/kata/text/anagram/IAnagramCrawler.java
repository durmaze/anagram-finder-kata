package kata.text.anagram;

import java.util.List;

/**
 * 
 * IAnagramCrawler searches for anagrams in a Word repository. 
 * 
 * @author Erkan Durmaz
 * @date 17 Mar 2013
 * @time 18:53:04
 *
 */
public interface IAnagramCrawler
{
	public List<List<Word>> findAllAnagrams();
	public void close();
//	public List<Word> findAnagramsByWord(Word searchWord);
//	public List<List<Word>> findAnagramsByWords(List<Word> searchWords);
}