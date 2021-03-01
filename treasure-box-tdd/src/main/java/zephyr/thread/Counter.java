package zephyr.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zephyr on 2019-10-03.
 */
public class Counter {
    private AtomicInteger counter;

    public Counter() {
        this.counter = new AtomicInteger();
    }

    public int value() {
        return counter.get();
    }

    public void increment() {
        counter.incrementAndGet();
    }
}
