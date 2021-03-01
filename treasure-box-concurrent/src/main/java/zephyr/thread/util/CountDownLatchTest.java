package zephyr.thread.util;

import lombok.extern.slf4j.Slf4j;
import zephyr.utils.SleepUtils;

import java.util.concurrent.CountDownLatch;

/**
 * Created by zephyr on 2020/6/2.
 */
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
