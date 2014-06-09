package kata.text.concurrency;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class KataThreadFactory implements ThreadFactory
{
	// dependencies & invariants
	private final String poolName;
	private final AtomicInteger threadNumber = new AtomicInteger(1);

	public KataThreadFactory(String poolName)
	{
		// inject dependency
		if (poolName == null || poolName.isEmpty())
		{
			throw new NullPointerException("poolName is null or empty");
		}
		
		this.poolName = poolName;
	}
	
	@Override
	public Thread newThread(Runnable runnable)
	{
		String threadName = this.poolName + "-Thread-" + this.threadNumber.getAndIncrement();
		
		return new Thread(runnable, threadName); 
	}
}
