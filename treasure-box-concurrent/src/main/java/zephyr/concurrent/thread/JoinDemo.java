package zephyr.concurrent.thread;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import zephyr.concurrent.utils.SleepUtils;

@Slf4j
public class JoinDemo {

    public static void main(String[] args) {
        Thread previous = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            // 每个线程拥有前一个线程的引用，需要等待前一个线程终止，才能从等待返回
            Thread thread = new Thread(new Domino(previous), "domino-" + i);
            thread.start();
            previous = thread;
        }
        SleepUtils.second(5);
        log.info("{} terminate.", Thread.currentThread().getName());
    }


    static class Domino implements Runnable {
        private final Thread thread;

        public Domino(Thread thread) {
            this.thread = thread;
        }

        @SneakyThrows
        @Override
        public void run() {
            thread.join();
            log.info("{} terminate.", Thread.currentThread().getName());
        }
    }

}
