package kata.text.anagram;

/**
 * 
 * Word is the domain class representing words in a language. Therefore, non-alphabetic characters are not allowed. 
 * 
 * @author Erkan Durmaz
 * @date 17 Mar 2013
 * @time 18:58:50
 *
 */
public class Word
{
	// dependency
	private final String word;

	public Word(String word)
	{
		// inject dependency
		if (word == null)
		{
			throw new NullPointerException("word is null");
		}
		
		word = word.trim();
		
		if (word.isEmpty())
		{
			throw new IllegalArgumentException("word contains no characters");
		}

		if (!containsOnlyLetters(word))
		{
			throw new IllegalArgumentException("word contains non-alpha characters");
		}

		this.word = word;
	}

	private Word(String word, boolean ignoreChecks)
	{
		/*
		 * Since it is a private constructor, we've already know that we have a valid Word.
		 * Therefore, we don't need to bother for the invariant protection.
		 */
		
		this.word = word;
	}

	/**
	 * Returns the length of the word
	 * 
	 * @return
	 */
	public int length()
	{
		return this.word.length();
	}

	/**
	 * Returns a new Word where all characters are in lower case
	 * 
	 * @return
	 */
	public Word toLowerCase()
	{
		return new Word(this.word.toLowerCase(), true);
	}

	/**
	 * Returns a new Word where all characters are in upper case
	 * 
	 * @return
	 */
	public Word toUpperCase()
	{
		return new Word(this.word.toUpperCase(), true);
	}

	/**
	 * Returns a copy of the charaters in the Word
	 * 
	 * @return
	 */
	public char[] toCharArray()
	{
		return this.word.toCharArray();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		
		return prime * result + this.word.hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		if (obj == null)
		{
			return false;
		}
		
		if (getClass() != obj.getClass())
		{
			return false;
		}

		Word other = (Word) obj;
		
		return this.word.equals(other.word);
	}

	@Override
	public String toString()
	{
		return this.word;
	}

	private boolean containsOnlyLetters(String word)
	{
		boolean containsOnlyLetters = true;

		for (char ch : word.toCharArray())
		{
			if (!Character.isLetter(ch))
			{
				containsOnlyLetters = false;
				break;
			}
		}

		return containsOnlyLetters;
	}
}
