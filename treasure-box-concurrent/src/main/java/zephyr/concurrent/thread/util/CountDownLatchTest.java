package zephyr.concurrent.thread.util;

import lombok.extern.slf4j.Slf4j;
import zephyr.concurrent.utils.SleepUtils;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class CountDownLatchTest {

    static CountDownLatch c = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            log.info("1");
            c.countDown();
            SleepUtils.second(3);
            log.info("2");
            c.countDown();
        }).start();

        c.await();
        log.info("3");
    }

}
