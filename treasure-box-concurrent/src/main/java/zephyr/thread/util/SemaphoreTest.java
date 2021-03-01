package zephyr.thread.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by zephyr on 2020/6/2.
 */
@Slf4j
public class SemaphoreTest {

    private static final int THREAD_COUNT = 30;

    private static final ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);
    private static final Semaphore s = new Semaphore(10);

    public static void main(String[] args) {
        // 30个线程，最多只允许10个线程同时执行
        for (int i = 0; i < THREAD_COUNT; i++) {
            threadPool.execute(() -> {
                try {
                    s.acquire(); // 阻塞直到获得许可
                    log.info("save data");
                    s.release(); // 释放许可
                } catch (InterruptedException ignored) {
                }
            });
        }
        threadPool.shutdown();
    }
}
