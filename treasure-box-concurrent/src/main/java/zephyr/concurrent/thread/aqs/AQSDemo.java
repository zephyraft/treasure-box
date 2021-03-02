package zephyr.concurrent.thread.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;

@Slf4j
public class AQSDemo {

    static final NonReentrantLock lock = new NonReentrantLock();
    static final Condition notFull = lock.newCondition();
    static final Condition notEmpty = lock.newCondition();
    static final Queue<String> queue = new LinkedBlockingQueue<>();
    static final int queueSize = 10;

    public static void main(String[] args) throws InterruptedException {
        Thread producer = new Thread(() -> {
            lock.lock();
            try {
                // 队列满了，则等待
                while (queue.size() == queueSize) {
                    notEmpty.await();
                }
                log.info("produce");
                queue.add("ele");
                // 唤醒消费者
                notFull.signalAll();
            } catch (Exception e) {
                log.info(e.getMessage(), e);
            } finally {
                lock.unlock();
            }
        });
        Thread consumer = new Thread(() -> {
            lock.lock();
            try {
                // 队列空，则等待
                while (queue.size() == 0) {
                    notFull.await();
                }
                log.info("consume");
                final String ele = queue.poll();
                // 唤醒生产者
                notEmpty.signalAll();
            } catch (Exception e) {
                log.info(e.getMessage(), e);
            } finally {
                lock.unlock();
            }
        });
        producer.start();
        consumer.start();

        producer.join();
        consumer.join();
    }

}
