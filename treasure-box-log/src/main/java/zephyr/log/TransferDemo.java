package zephyr.log;

import lombok.extern.slf4j.Slf4j;
import zephyr.log.transfer.LogRunnable;
import zephyr.log.transfer.TransactionFactory;
import zephyr.log.transfer.Transfer;

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
