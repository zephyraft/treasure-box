package zephyr.tdd.thread;

import java.util.concurrent.atomic.AtomicInteger;


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
