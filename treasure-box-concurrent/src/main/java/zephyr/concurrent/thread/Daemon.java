package zephyr.concurrent.thread;

import lombok.extern.slf4j.Slf4j;
import zephyr.concurrent.utils.SleepUtils;

/**
 * Daemon线程被用作完成支持性工作，jvm退出时，Daemon线程的finally块并不一定会执行
 */
@Slf4j
public class Daemon {

    public static void main(String[] args) {
        Thread thread = new Thread(new DaemonRunner(), "DaemonRunner");
        thread.setDaemon(true);// 设置为Daemon线程
        thread.start();
    }

    static class DaemonRunner implements Runnable {
        @Override
        public void run() {
            try {
                SleepUtils.second(10);
            } finally {
                // 不能依靠Daemon线程的finally块确保执行关闭或清理资源
                log.info("DaemonThread finally run.");
            }
        }
    }

}
