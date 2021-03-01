package zephyr.singleton;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Singleton {

    private static final Singleton singleton = new Singleton();

    private Singleton() {
        log.info("生成了一个实例");
    }

    public static Singleton getInstance() {
        return singleton;
    }

}
