package zephyr.lombok.model;

import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class SynchronizedExample {
    @Synchronized
    public static void hello() {
        log.info("world");
    }

    @Synchronized
    public int answerToLife() {
        return 42;
    }

    @Synchronized
    public void foo() {
        log.info("bar");
    }
}
