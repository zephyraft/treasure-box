package zephyr.concurrent.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

@Slf4j
public class LockSupportTest {

    public static void main(String[] args) throws InterruptedException {
        log.info("begin park!");
        // 解除上一个park或是下一个
        LockSupport.unpark(Thread.currentThread());
        // 由于先调用了unpark，会立即返回，不会阻塞
        LockSupport.park();
        log.info("end park!");

        Thread thread = new Thread(() -> {
            log.info("child thread park!");
            LockSupport.park();
            log.info("child thread unpark!");
        });

        thread.start();
        Thread.sleep(1000);
        log.info("main thread begin unpark");
        LockSupport.unpark(thread);


        Thread thread2 = new Thread(() -> {
            log.info("child thread park!");
            while (!Thread.currentThread().isInterrupted()) {
                LockSupport.park();
            }
            log.info("child thread unpark!");
        });

        thread2.start();
        Thread.sleep(1000);
        log.info("main thread begin unpark");
        thread2.interrupt();
    }

}
