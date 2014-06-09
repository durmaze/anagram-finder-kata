package kata.text.anagram;

import java.util.List;

public interface IAnagramFinderFactory
{
	public IAnagramFinder createAnagramFinder(List<Word> words);
}
