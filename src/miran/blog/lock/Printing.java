package miran.blog.lock;

import java.util.concurrent.CountDownLatch;

public class Printing implements Runnable {

	private int count = 0;
	private CountDownLatch latch;
	
	public Printing() { }
	
	@Override
	public void run() {
		print();

		latch.countDown();
	}
	
	protected void print() {
		count++;
		System.out.println("This is " + count + " time printing");
	}
	
	public void setLatch(CountDownLatch latch) {
		this.latch = latch;
	}
}
