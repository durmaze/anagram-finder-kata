package kata.text.utils;

import java.util.Arrays;
import java.util.List;

public class CollectionUtils
{
	public static List<String> createListFromDelimitedText(String delimitedText, String delimiter)
	{
		if (delimitedText == null)
		{
			return null;
		}
		
		if (delimitedText.isEmpty() || delimiter == null || delimiter.isEmpty() || !delimitedText.contains(delimiter))
		{
			return Arrays.asList(delimitedText); 
		}
		else
		{
			return Arrays.asList(delimitedText.split(delimiter));
		}
	}
}
