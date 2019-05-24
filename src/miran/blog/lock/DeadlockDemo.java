package miran.blog.lock;

public class DeadlockDemo {
    private static byte[] lock1 = new byte[0];
    private static byte[] lock2 = new byte[0];

    public static void main(String[] args) {
        new Thread(() -> {
                try {
                    synchronized (lock1) {
                        System.out.println("thread1 get lock1");
                        Thread.sleep(2000);
                        synchronized (lock2) {
                            System.out.println("thread1 get lock2");
                        }
                    }
                } catch (Throwable t) {
                    System.out.println("thread1 exception");
                }
        }).start();

        new Thread(() -> {
            try {
                synchronized (lock2) {
                    System.out.println("thread2 get lock2");
                    Thread.sleep(2000);
                    synchronized (lock1) {
                        System.out.println("thread2 get lock1");
                    }
                }
            } catch (Throwable t) {
                System.out.println("thread1 exception");
            }
        }).start();
    }
}
