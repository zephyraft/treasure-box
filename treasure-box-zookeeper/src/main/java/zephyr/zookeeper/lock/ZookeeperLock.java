package zephyr.zookeeper.lock;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

/**
 * curator客户端 操作zookeeper
 */
@Slf4j
public class ZookeeperLock {

    // 定义共享资源
    private static int NUMBER = 10;

    private static void printNumber() {
        // 业务逻辑: 秒杀
        log.info("*********业务方法开始************");
        log.info("当前的值: {}", NUMBER);
        NUMBER--;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("*********业务方法结束************");

    }

    private static CuratorFramework getClient() {
        // 定义重试的侧策略 1000 等待的时间(毫秒) 10 重试的次数
        RetryPolicy policy = new ExponentialBackoffRetry(1000, 10);

        // 定义zookeeper的客户端
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", policy);
        // 启动客户端
        client.start();
        return client;
    }


    public static void main(String[] args) {
        // 在zookeeper中定义一把锁
        final InterProcessMutex lock = new InterProcessMutex(getClient(), "/zephyr/zookeeper/lock");

        //启动是个线程
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    if (lock.acquire(15, TimeUnit.SECONDS)) {
                        try {
                            // do some work inside of the critical section here
                            printNumber();
                        } finally {
                            lock.release();
                        }
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }).start();
        }

    }

}
