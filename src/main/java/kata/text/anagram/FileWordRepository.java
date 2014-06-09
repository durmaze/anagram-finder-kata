package kata.text.anagram;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import kata.text.anagram.exception.AnagramExceptionCodes;
import kata.text.exception.KataException;

/**
 * 
 * FileWordRepository is a file-backed implementation of a IWordRepository. 
 * 
 * Words stored in the file must be delimited by new line.
 * 
 * @author Erkan Durmaz
 * @date 17 Mar 2013
 * @time 18:36:25
 *
 */
public class FileWordRepository implements IWordRepository
{
	// constants
	private static final String UTF_8_ENCODING = "UTF-8";

	// dependency
	private final File repositoryFile;

	public FileWordRepository(File repositoryFile)
	{
		// inject dependency
		if (repositoryFile == null)
		{
			throw new NullPointerException("repositoryFile is null");
		}

		if (!repositoryFile.exists())
		{
			// FileNotFoundException atilabilirdi. Checked exception oldugundan dolayi KataException attim
			throw new KataException(AnagramExceptionCodes.WORDREPOSITORY_REPOSITORY_FILE_CANNOT_BE_LOCATED, repositoryFile.getAbsolutePath());
		}

		this.repositoryFile = repositoryFile;
	}

	@Override
	public List<Word> findAllWords()
	{
		Scanner fileScanner = null;

		try
		{
			// get content from file
			fileScanner = new Scanner(this.repositoryFile, UTF_8_ENCODING);

			List<Word> words = new ArrayList<Word>();

			while (fileScanner.hasNextLine())
			{
				// escape apostrophes
				String nextLine = fileScanner.nextLine().replaceAll("'", "");
				
				if (!nextLine.trim().isEmpty())
				{
					words.add(new Word(nextLine));
				}
			}

			return !words.isEmpty() ? words : null;
		}
		catch (Exception e)
		{
			throw new KataException(e, AnagramExceptionCodes.WORDREPOSITORY_INVALID_CONTENT);
		}
		finally
		{
			if (fileScanner != null)
			{
				fileScanner.close();
			}
		}
	}

	@Override
	public Set<Word> findDistinctWords()
	{
		List<Word> allWords = findAllWords();
		
		return (allWords != null) ? new HashSet<Word>(allWords) : null;
	}
}
