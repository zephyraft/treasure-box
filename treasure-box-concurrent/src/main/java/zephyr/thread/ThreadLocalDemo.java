package zephyr.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * 每个线程内部都有一个名为threadLocals的hashMap（ThreadLocalMap），key为ThreadLocal变量的this引用红，value为set的值，
 * 因为每个线程可能会有多个ThreadLocal变量
 * 使用完毕后记得remove方法清除ThreadLocal本地变量
 * Created by zephyr on 2020/6/1.
 */
@Slf4j
public class ThreadLocalDemo {

    // 不支持继承
    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();
    // 可继承
    // 使用场景：比如子线程需要使用存放在threadlocal变量中的用户登录信息，再比如一些中间件需要把统一的id追踪的整个调用链路记录下来
    private static final ThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) {
        threadLocal.set("hello world");
        new Thread(() -> log.info("thread:{}", threadLocal.get())).start();
        log.info("main:{}", threadLocal.get());
        threadLocal.remove();

        inheritableThreadLocal.set("hello world");
        new Thread(() -> log.info("thread:{}", inheritableThreadLocal.get())).start();
        log.info("main:{}", inheritableThreadLocal.get());
        inheritableThreadLocal.remove();
    }
}
