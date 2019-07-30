package miran.blog.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class LockPerf implements Perf {

	class LockPrinting extends Printing {
		private ReentrantLock lock = new ReentrantLock();

		protected void print() {
			try {
				lock.lock();

				super.print();
			} finally {
				lock.unlock();
			}
		}
	}

	@Override
	public void doReport(long elapsed, TimeUnit unit) {
	}

	public static void main(String[] args) {
		SynchronizedPerf perf = new SynchronizedPerf();
		
		int threadNum = 2000;
		Printing printing = perf.new SyncPrinting();
		
		try {
			perf.doPerf(threadNum, printing);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
