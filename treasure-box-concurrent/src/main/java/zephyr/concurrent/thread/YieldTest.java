package zephyr.concurrent.thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class YieldTest implements Runnable {

    YieldTest() {
        new Thread(this).start();
    }

    public static void main(String[] args) {
        new YieldTest();
        new YieldTest();
        new YieldTest();
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            // i = 0时，让出cpu执行权，放弃时间片，进行下一轮调度
            if ((i % 5) == 0) {
                log.info("{} yield cpu...", Thread.currentThread());
                // 一般很少使用这个方法，在调试或者测试时这个方法或许可以帮助复现由于并发竞争条件导致的问题，其在设计并发控制时或许会有用途
                Thread.yield();
            }
        }
        log.info("{} is over", Thread.currentThread());
    }
}

