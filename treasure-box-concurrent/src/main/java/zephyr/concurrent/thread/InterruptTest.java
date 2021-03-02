package zephyr.concurrent.thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InterruptTest {

    public static void main(String[] args) throws InterruptedException {
        Thread threadOne = new Thread(() -> {
            while (!Thread.interrupted()) { // 中断标志为true时退出循环，并清除中断标志
            }
            log.info("threadOne isInterrupted:{}", Thread.currentThread().isInterrupted());
        });

        threadOne.start();
        // 设置中的标志
        threadOne.interrupt();
        threadOne.join();
        log.info("case one is over");

        Thread threadTwo = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                // 中断标志为true时退出循环
            }
        });

        threadTwo.start();
        threadTwo.interrupt();
        log.info("threadTwo isInterrupted:{}", threadTwo.isInterrupted());
        log.info("threadTwo isInterrupted:{}", threadTwo.interrupted()); // 获取的其实是当前线程 即主线程的中断标志
        log.info("threadTwo isInterrupted:{}", Thread.interrupted());
        log.info("threadTwo isInterrupted:{}", threadTwo.isInterrupted());

        threadTwo.join();
        log.info("case two is over");
    }

}
