package zephyr.concurrent.thread;

import lombok.extern.slf4j.Slf4j;
import zephyr.concurrent.utils.SleepUtils;

@Slf4j
public class InterruptedDemo {

    public static void main(String[] args) {
        Thread sleepThread = new Thread(new SleepRunner(), "SleepThread");
        sleepThread.setDaemon(true);

        Thread busyThread = new Thread(new BusyRunner(), "BusyThread");
        busyThread.setDaemon(true);

        sleepThread.start();
        busyThread.start();

        // 休眠5秒 让sleepThread和busyThread充分运行
        SleepUtils.second(5);
        sleepThread.interrupt();
        busyThread.interrupt();

        log.info("SleepThread interrupted is {}", sleepThread.isInterrupted());
        log.info("BusyThread interrupted is {}", busyThread.isInterrupted());

        // 防止立即退出
        SleepUtils.second(2);
    }

    static class SleepRunner implements Runnable {
        @Override
        public void run() {
            while (true) {
                SleepUtils.second(10);
            }
        }
    }

    static class BusyRunner implements Runnable {
        @Override
        public void run() {
            while (true) {

            }
        }
    }

}
