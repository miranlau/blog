package miran.blog.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 在很多情况下，可能有多个线程需要访问数目很少的资源。假想在服务器上运行着若干个回答客户端请求的线程。这些线程需要连接到同一数据库，但任一时刻 
 * 只能获得一定数目的数据库连接。你要怎样才能够有效地将这些固定数目的数据库连接分配给大量的线程？ 
 *   
 * <p>1.给方法加同步锁，保证同一时刻只能有一个人去调用此方法，其他所有线程排队等待，但是此种情况下即使你的数据库链接有10个，也始终只有一个处于使
 *  用状态。这样将会大大的浪费系统资源，而且系统的运行效率非常的低下。
 * <p>2.另外一种方法当然是使用信号量，通过信号量许可与数据库可用连接数相同的数目，将大大的提高效率和性能。
 *
 */
public class SemaphoreTest {

	public static void main(String[] args) throws InterruptedException {
		Semaphore semaphore = new Semaphore(2);
		Bank bank = new Bank(2, semaphore);
		bank.close();
		
		ExecutorService es = Executors.newCachedThreadPool();
		for (int i = 1000; i < 1010; i++) {
			es.submit(new Customer(semaphore, i, "客户" + i));
		}
		
		Thread.sleep(2000l);
		bank.open();
		
	}
}

class Bank {
	private int counter;
	private Semaphore semaphore;
	
	public Bank(int counter, Semaphore semaphore) {
		assert semaphore != null;
		assert counter == semaphore.availablePermits();
		this.counter = counter;
		this.semaphore = semaphore;
	}
	
	public void open() {
		if (semaphore != null) {
			semaphore.release(counter);
			System.out.println("银行开始办理业务");
		}
	}
	
	public void rest() {
		if (semaphore != null) {
			semaphore.drainPermits();
			System.out.println("银行暂停办理业务");
		}
	}
	
	public void close() {
		if (semaphore != null) {
			semaphore.acquireUninterruptibly(counter);
			System.out.println("银行停止办理业务");
		}
	}
}

class Customer implements Runnable {
	private Semaphore semaphore;
	private int cash;
	private String name;
	
	public Customer(Semaphore semaphore, int cash, String name) {
		this.semaphore = semaphore;
		this.cash = cash;
		this.name = name;
	}
	
	@Override
	public void run() {
		if (semaphore.availablePermits() < 1) {
			;
			System.out.println(name + " 现在没有空闲的柜台，请排队等待， 当前排队人数：" + semaphore.getQueueLength());
		} else {
			System.out.println(name + " 现在有空闲的柜台，请办理业务， 当前排队人数：" + semaphore.getQueueLength());
		}
		
		try {
			semaphore.acquire();
			saveCash();
			semaphore.release();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void saveCash() {
		System.out.println(name + " 您已成功存入" + cash);
	}
	
}