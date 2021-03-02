package zephyr.concurrent.thread.pool;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ConnectionPoolTest {

    static ConnectionPool pool = new ConnectionPool(10);
    // 保证同时开始
    static CountDownLatch start = new CountDownLatch(1);
    // 等候ConnectionRunner全部结束
    static CountDownLatch end;

    @SneakyThrows
    public static void main(String[] args) {
        int threadCount = 50; // 线程数
        end = new CountDownLatch(threadCount);
        int count = 20; // 执行获取连接的次数
        AtomicInteger got = new AtomicInteger();
        AtomicInteger notGot = new AtomicInteger();
        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(new ConnectionRunner(count, got, notGot), "ConnectionRunnerThread");
            thread.start();
        }
        start.countDown(); // 通知ConnectionRunner同时开始
        end.await(); // 等候ConnectionRunner全部结束
        log.info("total invoke: {}", threadCount * count);
        log.info("got connection: {}", got);
        log.info("not got connection: {}", notGot);
    }

    static class ConnectionRunner implements Runnable {

        int count;
        AtomicInteger got;
        AtomicInteger notGot;

        public ConnectionRunner(int count, AtomicInteger got, AtomicInteger notGot) {
            this.count = count;
            this.got = got;
            this.notGot = notGot;
        }

        @SneakyThrows
        @Override
        public void run() {
            start.await(); // 等候start countDown

            while (count > 0) {
                try {
                    // 1000ms内未获取到时为null
                    // 分别统计got和notGot
                    Connection connection = pool.fetchConnection(1000);
                    if (connection != null) {
                        try {
                            connection.createStatement();
                            connection.commit();
                        } finally {
                            pool.releaseConnection(connection);
                            got.incrementAndGet();
                        }
                    } else {
                        notGot.incrementAndGet();
                    }
                } catch (Exception ignored) {
                } finally {
                    count--;
                }
            }
            end.countDown();
        }
    }

}
