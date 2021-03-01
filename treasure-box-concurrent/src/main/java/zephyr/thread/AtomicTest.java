package zephyr.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by zephyr on 2020/6/3.
 */
@Slf4j
public class AtomicTest {

    private static AtomicLong atomicLong = new AtomicLong();

    private static Integer[] arrayOne = new Integer[]{0,1,2,3,0,5,6,0,56,0};
    private static Integer[] arrayTwo = new Integer[]{10,1,2,3,0,5,6,0,56,0};

    public static void main(String[] args) throws InterruptedException {
        Thread threadOne = new Thread(() -> {
            int size = arrayOne.length;
            for (int i = 0; i < size; i++) {
                if (arrayOne[i].equals(0)) {
                    atomicLong.incrementAndGet();
                }
            }
        });
        Thread threadTwo = new Thread(() -> {
            int size = arrayTwo.length;
            for (int i = 0; i < size; i++) {
                if (arrayTwo[i].equals(0)) {
                    atomicLong.incrementAndGet();
                }
            }
        });
        threadOne.start();
        threadTwo.start();

        threadOne.join();
        threadTwo.join();
        log.info("count 0: {}", atomicLong.get());
    }
}
