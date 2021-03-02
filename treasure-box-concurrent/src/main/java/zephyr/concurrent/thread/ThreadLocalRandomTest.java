package zephyr.concurrent.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class ThreadLocalRandomTest {

    public static void main(String[] args) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < 10; i++) {
            log.info("{}", random.nextInt(5));
        }
    }

}
