package zephyr.lazyinit;

import zephyr.model.Instance;

/**
 * 基于双锁检查的实例延迟初始化
 * Created by zephyr on 2020/6/1.
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
