package miran.blog.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public interface Perf {
	
	void doReport(long elapsed, TimeUnit unit);
	
	default void doPerf(int threadNum, Printing task) throws Exception {
		assert threadNum > 0;
		assert task != null;
		
		CountDownLatch latch = new CountDownLatch(threadNum);
		task.setLatch(latch);
		
		Thread[] printers = new Thread[threadNum];
		for (int i = 0; i < threadNum; i++) {
			printers[i] = new Thread(task);
		}
		
		long start = System.currentTimeMillis();
		for (int i = 0; i < threadNum; i++) {
			printers[i].start();
		}

		latch.await();
		long stop = System.currentTimeMillis();
		
		System.out.println(task.getClass().getName() + " elapsed time: " + (stop - start) + " milli seconds");
		doReport((stop - start), TimeUnit.MILLISECONDS);
	}
	

}
