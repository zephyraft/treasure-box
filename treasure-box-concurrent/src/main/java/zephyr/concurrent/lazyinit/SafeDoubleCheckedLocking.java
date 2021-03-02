package zephyr.concurrent.lazyinit;

/**
 * 基于双锁检查的实例延迟初始化
 */
public class SafeDoubleCheckedLocking {

    private volatile static Instance instance;

    public static Instance getInstance() {
        if (instance == null) {
            synchronized (SafeDoubleCheckedLocking.class) {
                if (instance == null) {
                    instance = new Instance(); // instance 为volatile，避免创建实例的过程中指令重排序
                }
            }
        }
        return instance;
    }

}
