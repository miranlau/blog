package miran.blog.lock;

import java.util.concurrent.TimeUnit;

public class SynchronizedPerf implements Perf {
	
	class SyncPrinting extends Printing {
		private Object mutex = new Object();

		protected void print() {
			synchronized(mutex) {
				super.print();
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
