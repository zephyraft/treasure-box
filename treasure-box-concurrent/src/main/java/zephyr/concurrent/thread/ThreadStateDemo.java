package zephyr.concurrent.thread;

import lombok.extern.slf4j.Slf4j;
import zephyr.concurrent.utils.SleepUtils;

@Slf4j
public class ThreadStateDemo {

    public static void main(String[] args) {
        new Thread(new TimeWaitingDemo(), "TimeWaitingThread").start();
        new Thread(new WaitingDemo(), "WaitingThread").start();
        new Thread(new BlockedDemo(), "BlockedThread-1").start();
        new Thread(new BlockedDemo(), "BlockedThread-2").start();
    }

    static class TimeWaitingDemo implements Runnable {
        @Override
        public void run() {
            while (true) {
                SleepUtils.second(100);
            }
        }
    }

    static class WaitingDemo implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (WaitingDemo.class) {
                    try {
                        WaitingDemo.class.wait();
                    } catch (InterruptedException e) {
                        log.error(e.getMessage(), e);
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }

    static class BlockedDemo implements Runnable {
        @Override
        public void run() {
            synchronized (BlockedDemo.class) {
                while (true) {
                    SleepUtils.second(100);
                }
            }
        }
    }
}
