package kata.text.anagram;

public class AnagramCheckerFactory implements IAnagramCheckerFactory
{
	@Override
	public IAnagramChecker createAnagramChecker()
	{
		return new SimpleAnagramChecker();
	}
}
