package zephyr.concurrent.thread.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class ExchangerTest {

    private static final Exchanger<String> exchanger = new Exchanger<>();
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        threadPool.execute(() -> {
            try {
                String a = " 银行流水 A";
                exchanger.exchange(a);
            } catch (InterruptedException ignored) {
            }
        });

        threadPool.execute(() -> {
            try {
                String b = " 银行流水 B";
                String a = exchanger.exchange(b);
                log.info("A和B是否一致：{}, A录入的是{}, B录入的是{}", a.equals(b), a, b);
            } catch (InterruptedException ignored) {
            }
        });

        threadPool.shutdown();
    }

}
