package zephyr.log4j2;

import lombok.extern.slf4j.Slf4j;
import zephyr.log4j2.transfer.LogRunnable;
import zephyr.log4j2.transfer.TransactionFactory;
import zephyr.log4j2.transfer.Transfer;

import java.util.concurrent.*;

@Slf4j
public class TransferDemo {

    public static void main(String[] args) {
        ExecutorService executor = new ThreadPoolExecutor(
                3,
                3,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(100),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        TransactionFactory transactionFactory = new TransactionFactory();
        for (int i = 0; i < 10; i++) {
            Transfer tx = transactionFactory.newInstance();
            Runnable task = new LogRunnable(tx);
            executor.submit(task);
        }
        executor.shutdown();
        log.error("", new RuntimeException("test error"));
    }
}
