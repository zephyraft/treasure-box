package zephyr.thread;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import zephyr.utils.SleepUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 等待/通知范式
 * Created by zephyr on 2020/6/1.
 */
@Slf4j
public class WaitNotifyDemo {

    static final Object lock = new Object();
    static boolean flag = true;

    public static void main(String[] args) {
        Thread waitThread = new Thread(new Wait(), "WaitThread");
        waitThread.start();
        SleepUtils.second(1);
        Thread notifyThread = new Thread(new Notify(), "NotifyThread");
        notifyThread.start();
    }

    private static String currentDateTime() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    static class Wait implements Runnable {
        @SneakyThrows
        @Override
        public void run() {
            // 加锁，拥有lock的Monitor
            synchronized (lock) {
                // 条件不满足时，继续wait，释放lock的锁
                while (flag) {
                    log.info("{} flag is true. wait @ {}", Thread.currentThread(), currentDateTime());
                    lock.wait(); // wait会释放对象的锁
                }

                // 条件满足时，完成工作
                log.info("{} flag is false. running @ {}", Thread.currentThread(), currentDateTime());
            }
        }
    }

    static class Notify implements Runnable {
        @SneakyThrows
        @Override
        public void run() {
            // 加锁，拥有lock的Monitor
            synchronized (lock) {
                // 获取lock的锁，进行通知，通知不会释放lock的锁
                // 直到当前线程释放了lock后，WaitThread才能从wait方法中返回
                log.info("{} hold lock. notify @ {}", Thread.currentThread(), currentDateTime());
                lock.notifyAll();
                flag = false;
                SleepUtils.second(5);
            }

            // 再次加锁
            synchronized (lock) {
                log.info("{} hold lock again. sleep @ {}", Thread.currentThread(), currentDateTime());
                SleepUtils.second(5);
            }
        }
    }
}
