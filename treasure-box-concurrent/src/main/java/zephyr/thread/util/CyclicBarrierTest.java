package zephyr.thread.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by zephyr on 2020/6/2.
 */
@Slf4j
public class CyclicBarrierTest {

    static CyclicBarrier c = new CyclicBarrier(2);

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                c.await();
            } catch (InterruptedException | BrokenBarrierException ignore) {
            }
            log.info("1");
        }).start();

        try {
            c.await();
        } catch (Exception ignored) {
        }
        log.info("2");
    }

}
