package kata.text.anagram;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * AnagramFinder finds the anagrams of the words with the help of a IAnagramChecker. Implementation of the IAnagramChecker
 * can vary (i.e. faster anagram checkers or language-dependent anagram checkers can be injected).
 * 
 * AnagramFinder uses a Marking algorithm for multiple search words, as described below:
 * 
 * Anagram function is transitive, meaning;
 * - if anagramOf(word1) is word2 and word3, 
 * - then anagramOf(word2) is word1 and word3,
 * - and anagramOf(word3) is word1 and word2.
 * 
 * Therefore, if we've found the anagrams of word1, then we dont need to find anagrams of word2 and word3 
 * (since we've already found word1, word2 and word3 are anagrams). 
 * 
 * AnagramFinder honors this transitivity and skips the anagram-check of Marked words 
 * 
 * @author Erkan Durmaz
 * @date 17 Mar 2013
 * @time 18:07:37
 *
 */
public class AnagramFinder implements IAnagramFinder
{
	// dependency
	private final IAnagramChecker anagramChecker;

	public AnagramFinder(IAnagramChecker anagramChecker)
	{
		// inject dependency
		if (anagramChecker == null)
		{
			throw new NullPointerException("anagramChecker is null");
		}

		this.anagramChecker = anagramChecker;
	}

	@Override
	public List<Word> findAnagrams(Word searchedWord, List<Word> words)
	{
		if (searchedWord == null)
		{
			throw new NullPointerException("searchedWord is null");
		}

		if (words == null || words.isEmpty())
		{
			throw new NullPointerException("words is null or empty");
		}

		List<Word> anagrams = new ArrayList<Word>();

		for (Word nextWord : words)
		{
			boolean isAnagram = this.anagramChecker.checkWords(searchedWord, nextWord);

			if (isAnagram)
			{
				anagrams.add(nextWord);
			}
		}

		return !anagrams.isEmpty() ? anagrams : null;
	}

	@Override
	public List<List<Word>> findAllAnagrams(List<Word> words)
	{
		if (words == null || words.isEmpty())
		{
			throw new NullPointerException("words is null or empty");
		}

		// list of the anagrams that we should return
		List<List<Word>> listOfAnagrams = new ArrayList<List<Word>>();
		
		// anagram marks of the words
		Mark[] anagramMarks = createMarks(words);

		for (Word searchedWord : words)
		{
			// anagrams of the searched word
			List<Word> anagrams = new ArrayList<Word>();

			for (int i = 0; i < words.size(); i++)
			{
				Word nextWord = words.get(i);
				
				// if we haven't searched nextWord's anagrams before, search it
				if (!anagramMarks[i].isMarked())
				{
					boolean isAnagram = this.anagramChecker.checkWords(searchedWord, nextWord);
					
					if (isAnagram)
					{
						// add to anagrams list
						anagrams.add(nextWord);

						// mark nextWord so that we don't bother searching of its anagrams 
						anagramMarks[i].mark();
					}
				}
			}

			listOfAnagrams.add(anagrams);
		}
		
		return !listOfAnagrams.isEmpty() ? listOfAnagrams : null;
	}

	private Mark[] createMarks(List<Word> words)
	{
		Mark[] anagramMarks = new Mark[words.size()];
		
		for (int i = 0; i < anagramMarks.length; i++)
		{
			anagramMarks[i] = new Mark();
		}
		
		return anagramMarks;
	}
}
