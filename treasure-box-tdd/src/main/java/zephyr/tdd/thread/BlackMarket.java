package zephyr.tdd.thread;

import java.util.concurrent.atomic.AtomicInteger;


public class BlackMarket {
    private final Object ticket = new Object();
    private final AtomicInteger ticketCount = new AtomicInteger(0);

    public void buyTicket() throws InterruptedException {
        synchronized (ticket) {
            while (ticketCount.get() <= 0) {
                ticket.wait();
            }
        }
    }

    public void sellTicket() {
        synchronized (ticket) {
            ticketCount.incrementAndGet();
            ticket.notifyAll();
        }
    }
}
