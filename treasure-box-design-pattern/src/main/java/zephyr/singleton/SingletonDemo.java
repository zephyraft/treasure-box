package zephyr.singleton;

import lombok.extern.slf4j.Slf4j;

/**
 * 单例模式 - 确保只生成一个实例
 *
 * 单例模式 - 1个角色
 * Singleton：单例，有返回唯一实例的static方法
 * @see zephyr.singleton.Singleton
 *
 * 确保只生成一个实例，避免多实例之间互相影响
 * 第一次调用getInstance时Singleton被初始化
 *
 * 相关设计模式：
 * AbstractFactory
 * Builder
 * Facade
 * Prototype
 *
 */
@Slf4j
public class SingletonDemo {

    public static void main(String[] args) {
        log.info("Start.");
        Singleton obj1 = Singleton.getInstance();
        Singleton obj2 = Singleton.getInstance();
        if (obj1 == obj2) {
            log.info("是相同实例");
        } else {
            log.info("是不同实例");
        }
        log.info("End.");
    }

}
