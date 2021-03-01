package zephyr.thread;

import lombok.extern.slf4j.Slf4j;
import zephyr.utils.SleepUtils;

/**
 * Created by zephyr on 2020/6/1.
 */
@Slf4j
public class ShutdownDemo {

    public static void main(String[] args) {
        Runner one = new Runner();
        Thread countThread = new Thread(one, "CountThread");
        countThread.start();
        // 睡眠一秒，main线程对CountThread进行中断，使CountThread能感知中断而结束
        SleepUtils.second(1);
        countThread.interrupt();

        Runner two = new Runner();
        countThread = new Thread(two, "CountThread");
        countThread.start();
        // 睡眠一秒，main线程对CountThread进行中断，使CountThread能感知on为false而结束
        SleepUtils.second(1);
        two.cancel();

    }


    private static class Runner implements Runnable {
        private long i;
        private volatile boolean on = true; // volatile保证所有线程对变量访问的可见性

        @Override
        public void run() {
            while (on && !Thread.currentThread().isInterrupted()) {
                i++;
            }
            log.info("Count i={}", i);
        }

        public void cancel() {
            on = false;
        }
    }

}
