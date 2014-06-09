package kata.text.utils;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ListSplitterTest
{
	@Test
	public void testSplitByCount_MaxSplitCountIsAFactorOfTheListSize() throws Exception
	{
		int listSize = 100;
		int maxSplitCount = 10;
		
		// populate
		List<String> mainList = createPopulatedList(listSize);
		
		// split
		ListSplitter<String> listSplitter = new ListSplitter<String>(mainList);
		List<List<String>> chunks = listSplitter.splitByCount(maxSplitCount);
		
		assertThat(chunks, is(notNullValue()));
		assertThat(chunks.size(), is(lessThanOrEqualTo(maxSplitCount)));
		
		int totalItemsCount = 0;
		
		for (List<String> chunk : chunks)
		{
			assertThat(chunk, is(notNullValue()));
			assertThat(chunk.size(), is(lessThanOrEqualTo((listSize / maxSplitCount))));
			
			totalItemsCount += chunk.size();
		}
		
		assertThat(totalItemsCount, is(equalTo(listSize)));
	}
	
	@Test
	public void testSplitByCount_MaxSplitCountIsNotAFactorOfTheListSize_1() throws Exception
	{
		int listSize = 95;
		int maxSplitCount = 10;
		
		// populate
		List<String> mainList = createPopulatedList(listSize);
		
		// split
		ListSplitter<String> listSplitter = new ListSplitter<String>(mainList);
		List<List<String>> chunks = listSplitter.splitByCount(maxSplitCount);
		
		assertThat(chunks, is(notNullValue()));
		assertThat(chunks.size(), is(lessThanOrEqualTo(maxSplitCount)));
		
		int totalItemsCount = 0;
		
		for (int i = 0; i < chunks.size(); i++)
		{
			List<String> chunk = chunks.get(i);
			assertThat(chunk, is(notNullValue()));
			
			if (i == (chunks.size() - 1))
			{
				assertThat(chunk.size(), is(equalTo(5)));
			}
			else
			{
				assertThat(chunk.size(), is(equalTo(10)));
			}
			
			totalItemsCount += chunk.size();
		}
		
		assertThat(totalItemsCount, is(equalTo(listSize)));
	}
	
	@Test
	public void testSplitByCount_MaxSplitCountIsNotAFactorOfTheListSize_2() throws Exception
	{
		int listSize = 43;
		int maxSplitCount = 10;
		
		// populate
		List<String> mainList = createPopulatedList(listSize);
		
		// split
		ListSplitter<String> listSplitter = new ListSplitter<String>(mainList);
		List<List<String>> chunks = listSplitter.splitByCount(maxSplitCount);
		
		assertThat(chunks, is(notNullValue()));
		assertThat(chunks.size(), is(lessThanOrEqualTo(maxSplitCount)));
		
		int totalItemsCount = 0;
		
		for (int i = 0; i < chunks.size(); i++)
		{
			List<String> chunk = chunks.get(i);
			assertThat(chunk, is(notNullValue()));
			
			if (i == (chunks.size() - 1))
			{
				assertThat(chunk.size(), is(equalTo(3)));
			}
			else
			{
				assertThat(chunk.size(), is(equalTo(5)));
			}
			
			totalItemsCount += chunk.size();
		}
		
		assertThat(totalItemsCount, is(equalTo(listSize)));
	}
	
	@Test
	public void testSplitByCount_ListSizeIsLessThanMaxSplitCount() throws Exception
	{
		int listSize = 5;
		int maxSplitCount = 10;
		
		// populate
		List<String> mainList = createPopulatedList(listSize);
		
		// split
		ListSplitter<String> listSplitter = new ListSplitter<String>(mainList);
		List<List<String>> chunks = listSplitter.splitByCount(maxSplitCount);
		
		assertThat(chunks, is(notNullValue()));
		assertThat(chunks.size(), is(lessThanOrEqualTo(maxSplitCount)));
		
		assertThat(chunks.size(), is(equalTo(listSize)));
		
		int totalItemsCount = 0;
		
		for (int i = 0; i < chunks.size(); i++)
		{
			List<String> chunk = chunks.get(i);
			assertThat(chunk, is(notNullValue()));
			assertThat(chunk.size(), is(equalTo(1)));
			
			totalItemsCount += chunk.size();
		}
		
		assertThat(totalItemsCount, is(equalTo(listSize)));
	}
	
	@Test
	public void testSplitByCount_MaxSplitCountIsOne() throws Exception
	{
		int listSize = 5;
		int maxSplitCount = 1;

		// populate
		List<String> mainList = createPopulatedList(listSize);

		// split
		ListSplitter<String> listSplitter = new ListSplitter<String>(mainList);
		List<List<String>> chunks = listSplitter.splitByCount(maxSplitCount);

		assertThat(chunks, is(notNullValue()));
		assertThat(chunks.size(), is(equalTo(1)));

		List<String> chunk = chunks.get(0);
		assertThat(chunk, is(notNullValue()));
		assertThat(chunk.size(), is(equalTo(listSize)));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSplitByCount_MaxSplitCountIsZero() throws Exception
	{
		int listSize = 5;
		int maxSplitCount = 0;
		
		// populate
		List<String> mainList = createPopulatedList(listSize);
		
		// split
		ListSplitter<String> listSplitter = new ListSplitter<String>(mainList);
		listSplitter.splitByCount(maxSplitCount);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSplitByCount_MaxSplitCountIsLessThanZero() throws Exception
	{
		int listSize = 5;
		int maxSplitCount = -5;
		
		// populate
		List<String> mainList = createPopulatedList(listSize);
		
		// split
		ListSplitter<String> listSplitter = new ListSplitter<String>(mainList);
		listSplitter.splitByCount(maxSplitCount);
	}

	@Test
	public void testSplitBySize_ChunkSizeIsAFactorOfTheListSize() throws Exception
	{
		int listSize = 100;
		int chunkSize = 10;
		
		List<String> mainList = createPopulatedList(listSize);
		
		// split
		ListSplitter<String> listSplitter = new ListSplitter<String>(mainList);
		List<List<String>> chunks = listSplitter.splitBySize(chunkSize);
		
		assertThat(chunks, is(notNullValue()));
		assertThat(chunks.size(), is(equalTo(listSize / chunkSize)));
		
		int totalItemsCount = 0;
		
		for (List<String> chunk : chunks)
		{
			assertThat(chunk, is(notNullValue()));
			assertThat(chunk.size(), is(equalTo(chunkSize)));
			
			totalItemsCount += chunk.size();
		}
		
		assertThat(totalItemsCount, is(equalTo(listSize)));
	}
	
	@Test
	public void testSplitBySize_ChunkSizeIsNotAFactorOfTheListSize_1() throws Exception
	{
		int listSize = 105;
		int chunkSize = 10;
		
		List<String> mainList = createPopulatedList(listSize);
		
		// split
		ListSplitter<String> listSplitter = new ListSplitter<String>(mainList);
		List<List<String>> chunks = listSplitter.splitBySize(chunkSize);
		
		assertThat(chunks, is(notNullValue()));
		assertThat(chunks.size(), is(equalTo((listSize / chunkSize) + 1)));
		
		int totalItemsCount = 0;
		
		for (int i = 0; i < chunks.size(); i++)
		{
			List<String> chunk = chunks.get(i);
			assertThat(chunk, is(notNullValue()));
			
			if (i == (chunks.size() - 1))
			{
				assertThat(chunk.size(), is(equalTo(listSize % chunkSize)));
			}
			else
			{
				assertThat(chunk.size(), is(equalTo(chunkSize)));
			}
			
			totalItemsCount += chunk.size();
		}
		
		assertThat(totalItemsCount, is(equalTo(listSize)));
	}
	
	@Test
	public void testSplitBySize_ChunkSizeIsNotAFactorOfTheListSize_2() throws Exception
	{
		int listSize = 95;
		int chunkSize = 10;
		
		List<String> mainList = createPopulatedList(listSize);
		
		// split
		ListSplitter<String> listSplitter = new ListSplitter<String>(mainList);
		List<List<String>> chunks = listSplitter.splitBySize(chunkSize);
		
		assertThat(chunks, is(notNullValue()));
		assertThat(chunks.size(), is(equalTo((listSize / chunkSize) + 1)));
		
		int totalItemsCount = 0;
		
		for (int i = 0; i < chunks.size(); i++)
		{
			List<String> chunk = chunks.get(i);
			assertThat(chunk, is(notNullValue()));
			
			if (i == (chunks.size() - 1))
			{
				assertThat(chunk.size(), is(equalTo(listSize % chunkSize)));
			}
			else
			{
				assertThat(chunk.size(), is(equalTo(chunkSize)));
			}
			
			totalItemsCount += chunk.size();
		}
		
		assertThat(totalItemsCount, is(equalTo(listSize)));
	}
	
	@Test
	public void testSplitBySize_ListSizeIsLessThanChunkSize() throws Exception
	{
		int listSize = 5;
		int chunkSize = 10;
		
		List<String> mainList = createPopulatedList(listSize);
		
		// split
		ListSplitter<String> listSplitter = new ListSplitter<String>(mainList);
		List<List<String>> chunks = listSplitter.splitBySize(chunkSize);
		
		assertThat(chunks, is(notNullValue()));
		assertThat(chunks.size(), is(equalTo(1)));

		List<String> chunk = chunks.get(0);
		assertThat(chunk.size(), is(equalTo(listSize)));
	}
	
	@Test
	public void testSplitBySize_ListSizeIsEqualToChunkSize() throws Exception
	{
		int listSize = 10;
		int chunkSize = 10;
		
		List<String> mainList = createPopulatedList(listSize);
		
		// split
		ListSplitter<String> listSplitter = new ListSplitter<String>(mainList);
		List<List<String>> chunks = listSplitter.splitBySize(chunkSize);
		
		assertThat(chunks, is(notNullValue()));
		assertThat(chunks.size(), is(equalTo(1)));

		List<String> chunk = chunks.get(0);
		assertThat(chunk.size(), is(equalTo(listSize)));
	}
	
	@Test
	public void testSplitBySize_ChunkSizeIsOne() throws Exception
	{
		int listSize = 25;
		int chunkSize = 1;
		
		List<String> mainList = createPopulatedList(listSize);
		
		// split
		ListSplitter<String> listSplitter = new ListSplitter<String>(mainList);
		List<List<String>> chunks = listSplitter.splitBySize(chunkSize);
		
		assertThat(chunks, is(notNullValue()));
		assertThat(chunks.size(), is(equalTo(listSize)));

		int totalItemsCount = 0;
		
		for (List<String> chunk : chunks)
		{
			assertThat(chunk, is(notNullValue()));
			assertThat(chunk.size(), is(equalTo(1)));
			
			totalItemsCount += chunk.size();
		}
		
		assertThat(totalItemsCount, is(equalTo(listSize)));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSplitBySize_ChunkSizeIsZero() throws Exception
	{
		int listSize = 25;
		int chunkSize = 0;
		
		List<String> mainList = createPopulatedList(listSize);
		
		// split
		ListSplitter<String> listSplitter = new ListSplitter<String>(mainList);
		listSplitter.splitBySize(chunkSize);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSplitBySize_ChunkSizeIsLessThanZero() throws Exception
	{
		int listSize = 25;
		int chunkSize = -5;
		
		List<String> mainList = createPopulatedList(listSize);
		
		// split
		ListSplitter<String> listSplitter = new ListSplitter<String>(mainList);
		listSplitter.splitBySize(chunkSize);
	}
	
	private List<String> createPopulatedList(int listSize)
	{
		List<String> mainList = new ArrayList<String>();
		
		for (int i = 0; i < listSize; i++)
		{
			mainList.add(String.valueOf(i));
		}
		return mainList;
	}
}
