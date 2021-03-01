package zephyr.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by zephyr on 2019-10-03.
 */
class TestThread {

    @Test
    void testBasicFunctionality() {
        Counter counter = new Counter();
        assertEquals(0, counter.value());
        counter.increment();
        assertEquals(1, counter.value());
        counter.increment();
        assertEquals(2, counter.value());
    }

    @Test
    void testForThreadSafety() throws InterruptedException, BrokenBarrierException {
        final Counter codeUnderTest = new Counter();
        final int numberOfThreads = 20;
        final int incrementsPerThread = 100;

        // 要算上当前线程
        CyclicBarrier entryBarrier = new CyclicBarrier(numberOfThreads + 1);
        CyclicBarrier exitBarrier = new CyclicBarrier(numberOfThreads + 1);

        Runnable runnable = () -> {
            for (int i = 0; i < incrementsPerThread; i++) {
                codeUnderTest.increment();
            }
        };

        for (int i = 0; i < numberOfThreads; i++) {
            new SynchedThread(runnable, entryBarrier, exitBarrier).start();
        }

        assertEquals(0, codeUnderTest.value());
        entryBarrier.await(); // 线程开始执行
        exitBarrier.await(); // 等待所有线程结束
        assertEquals(numberOfThreads * incrementsPerThread, codeUnderTest.value());
    }

    @Test
    void testBlockingBehavior() throws InterruptedException {
        final AtomicBoolean blocked = new AtomicBoolean(true);

        Thread buyer = new Thread(() -> {
            try {
                BlackMarket blackMarket = new BlackMarket();
                // blackMarket.sellTicket();
                blackMarket.buyTicket(); // 另一个线程调用阻塞方法
                blocked.set(false); // 若方法可行 设置标记
            } catch (InterruptedException expected) {
            }
        });

        await().atMost(2, TimeUnit.SECONDS).until(() -> {
            buyer.start();
            return true;
        });
        buyer.interrupt();
        buyer.join(1000L);
        assertFalse(buyer.isAlive());
        assertTrue(blocked.get());
    }
}
