package kata.text.anagram;

public class FrequencyBasedAnagramCheckerFactory implements IAnagramCheckerFactory
{
	@Override
	public IAnagramChecker createAnagramChecker()
	{
		return new FrequencyBasedAnagramChecker();
	}
}
