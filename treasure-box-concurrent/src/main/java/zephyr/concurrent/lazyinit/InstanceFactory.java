package zephyr.concurrent.lazyinit;

/**
 * 基于类初始化的实例延迟初始化
 */
public class InstanceFactory {

    public static Instance getInstance() {
        return InstanceHolder.instance; // 调用时初始化InstanceHolder类
    }

    // class对象初始化时会获取初始化锁
    private static class InstanceHolder {
        public static Instance instance = new Instance();
    }
}
