package miran.blog.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class DistributedLock {

    private static final String lockPath = "/lock/X";
    static CuratorFramework client = null;

    public static void main(String[] args) {
        TestingClusterDemo cluster = new TestingClusterDemo();
        try {
            new Thread(() -> {
                try {
                    cluster.startCluster();
                } catch (Exception e){}
            }).start();

            Thread.sleep(5000l);

            client = CuratorFrameworkFactory.builder()
                    .connectString("127.0.0.1:2081,127.0.0.1:2082,127.0.0.1:2083")
                    .sessionTimeoutMs(5000)
                    .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                    .build();
            client.start();

            final InterProcessMutex lock = new InterProcessMutex(client, lockPath);
            final CountDownLatch latch = new CountDownLatch(1);

            for(int i = 0; i < 20; i++) {
                new Thread(() -> {
                    try {
                        latch.await();
                        lock.acquire();
                    } catch(Exception e) {
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSS");
                        String id = sdf.format(new Date());
                        System.out.println(id);

                        try {
                            lock.release();
                        } catch (Exception el) {
                            //
                        }
                    }
                }).start();
            }

            latch.countDown();
        }catch (Exception e) {
        }
    }


}
