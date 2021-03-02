package zephyr.tdd.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CyclicBarrier;


@Slf4j
public class SynchedThread extends Thread{

    private CyclicBarrier entryBarrier;
    private CyclicBarrier exitBarrier;

    public SynchedThread(Runnable runnable, CyclicBarrier entryBarrier, CyclicBarrier exitBarrier) {
        super(runnable);
        this.entryBarrier = entryBarrier;
        this.exitBarrier = exitBarrier;
    }

    @Override
    public void run() {
        try {
            entryBarrier.await(); // 等待其他线程
            super.run(); // 执行
            exitBarrier.await(); // 暗示完成
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
