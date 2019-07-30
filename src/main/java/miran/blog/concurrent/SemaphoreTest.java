package miran.blog.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * �ںܶ�����£������ж���߳���Ҫ������Ŀ���ٵ���Դ�������ڷ����������������ɸ��ش�ͻ���������̡߳���Щ�߳���Ҫ���ӵ�ͬһ���ݿ⣬����һʱ�� 
 * ֻ�ܻ��һ����Ŀ�����ݿ����ӡ���Ҫ�������ܹ���Ч�ؽ���Щ�̶���Ŀ�����ݿ����ӷ�����������̣߳� 
 *   
 * <p>1.��������ͬ��������֤ͬһʱ��ֻ����һ����ȥ���ô˷��������������߳��Ŷӵȴ������Ǵ�������¼�ʹ������ݿ�������10����Ҳʼ��ֻ��һ������ʹ
 *  ��״̬��������������˷�ϵͳ��Դ������ϵͳ������Ч�ʷǳ��ĵ��¡�
 * <p>2.����һ�ַ�����Ȼ��ʹ���ź�����ͨ���ź�����������ݿ������������ͬ����Ŀ�����������Ч�ʺ����ܡ�
 *
 */
public class SemaphoreTest {

	public static void main(String[] args) throws InterruptedException {
		Semaphore semaphore = new Semaphore(2);
		Bank bank = new Bank(2, semaphore);
		bank.close();
		
		ExecutorService es = Executors.newCachedThreadPool();
		for (int i = 1000; i < 1010; i++) {
			es.submit(new Customer(semaphore, i, "�ͻ�" + i));
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
			System.out.println("���п�ʼ����ҵ��");
		}
	}
	
	public void rest() {
		if (semaphore != null) {
			semaphore.drainPermits();
			System.out.println("������ͣ����ҵ��");
		}
	}
	
	public void close() {
		if (semaphore != null) {
			semaphore.acquireUninterruptibly(counter);
			System.out.println("����ֹͣ����ҵ��");
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
			System.out.println(name + " ����û�п��еĹ�̨�����Ŷӵȴ��� ��ǰ�Ŷ�������" + semaphore.getQueueLength());
		} else {
			System.out.println(name + " �����п��еĹ�̨�������ҵ�� ��ǰ�Ŷ�������" + semaphore.getQueueLength());
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
		System.out.println(name + " ���ѳɹ�����" + cash);
	}
	
}