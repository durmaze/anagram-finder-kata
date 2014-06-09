package kata.text.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * ListSplitter splits a List into chunks either by count or chunk-size.
 * 
 * @author Erkan Durmaz
 * @date 17 Mar 2013
 * @time 19:07:42
 *
 * @param <T>
 */
public class ListSplitter<T>
{
	// dependency
	private final List<T> originalList;

	public ListSplitter(List<T> originalList)
	{
		// inject dependency
		if (originalList == null)
		{
			throw new NullPointerException("originalList is null");
		}

		this.originalList = originalList;
	}

	/**
	 * The returned list is backed by the original list, so non-structural changes in the returned list are reflected in this list, and vice-versa.
	 * 
	 * @param maxSplitCount denotes how many chunks will be created at most
	 * @return list of the chunks
	 */
	public List<List<T>> splitByCount(int maxSplitCount)
	{
		if (maxSplitCount <= 0)
		{
			throw new IllegalArgumentException("maxSplitCount cannot be a non-positive value");
		}

		// create returnedList
		List<List<T>> returnedList = new ArrayList<List<T>>();

		// return the original
		if (maxSplitCount == 1)
		{
			returnedList.add(this.originalList);
			return returnedList;
		}

		// chunkSize => ceilingOf(list.size() / maxSplitCount)
		int chunkSize = new Double(Math.ceil((new Double(this.originalList.size()) / maxSplitCount))).intValue();

		// populate returnedList
		for (int i = 0; i < maxSplitCount; i++)
		{
			int fromIndex = i * chunkSize;

			// prevent IndexOutOfBoundExceptions for fromIndex
			if (fromIndex < this.originalList.size())
			{
				// again prevent IndexOutOfBoundExceptions for toIndex
				int toIndex = (((i + 1) * chunkSize) < this.originalList.size()) ? (i + 1) * chunkSize : this.originalList.size();

				List<T> subList = this.originalList.subList(fromIndex, toIndex);

				if (!subList.isEmpty())
				{
					returnedList.add(subList);
				}
			}
		}

		return returnedList;
	}

	/**
	 * The returned list is backed by the original list, so non-structural changes in the returned list are reflected in this list, and vice-versa.
	 * 
	 * @param chunkSize size of the chunk
	 * @return list of the chunks
	 */
	public List<List<T>> splitBySize(int chunkSize)
	{
		if (chunkSize <= 0)
		{
			throw new IllegalArgumentException("chunkSize cannot be a non-positive value");
		}

		// create returnedList
		List<List<T>> returnedList = new ArrayList<List<T>>();

		// return the original
		if (chunkSize >= this.originalList.size())
		{
			returnedList.add(this.originalList);
			return returnedList;
		}

		int addedItemsCount = 0;
		List<T> subList = new ArrayList<T>(); // list to be added to the returnedList

		for (int i = 0; i < this.originalList.size(); i++)
		{
			subList.add(this.originalList.get(i));

			// add current chunk
			if ((i + 1) % chunkSize == 0)
			{
				returnedList.add(subList);
				subList = new ArrayList<T>();	// reset subList

				// count the added items (so that we can find leftovers)
				addedItemsCount += chunkSize;
			}
		}

		// add leftovers
		if (addedItemsCount < this.originalList.size())
		{
			// Since we've already added to the subList, we just need to add the sublist to the returnedList.
			returnedList.add(subList);
		}

		return returnedList;
	}

}
