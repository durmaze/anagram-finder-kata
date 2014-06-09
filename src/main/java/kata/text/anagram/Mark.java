package kata.text.anagram;

/**
 * 
 * Mark is a simple Helper class to keep track of markings 
 * 
 * @author Erkan Durmaz
 * @date 17 Mar 2013
 * @time 18:53:57
 *
 */
public class Mark
{
	private boolean isMarked;

	public boolean isMarked()
	{
		return isMarked;
	}

	public void mark()
	{
		this.isMarked = true;
	}
	
	public void unmark()
	{
		this.isMarked = false;
	}
}
