package kata.text.anagram;

import java.util.List;

public class AnagramFinderFactory implements IAnagramFinderFactory
{
	// dependency
	private final IAnagramCheckerFactory anagramCheckerFactory;

	public AnagramFinderFactory(IAnagramCheckerFactory anagramCheckerFactory)
	{
		// inject dependency
		if (anagramCheckerFactory == null)
		{
			throw new NullPointerException("anagramCheckerFactory is null");
		}

		this.anagramCheckerFactory = anagramCheckerFactory;
	}
	
	@Override
	public IAnagramFinder createAnagramFinder(List<Word> words)
	{
		return new AnagramFinder(this.anagramCheckerFactory.createAnagramChecker());
	}
}
