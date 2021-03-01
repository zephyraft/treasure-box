package zephyr.factorymethod;

import lombok.extern.slf4j.Slf4j;
import zephyr.factorymethod.framework.Product;
import zephyr.factorymethod.idcard.IDCardFactory;

/**
 * 工厂方法模式 - 构建生成实例的工厂，决定实例生成方式，但不决定所要生成的具体的类
 * 工厂方法模式在生成实例时一定使用到了模板方法模式
 * 将生成实例的框架和负责生成实例的类解耦
 *
 * 工厂方法模式 - 4个角色
 * Product：产品，定义了工厂方法模式中生成的实例的接口
 * @see zephyr.factorymethod.framework.Product
 * Creator：创建者，负责生成Product
 * @see zephyr.factorymethod.framework.Factory
 * ConcreteProduct：具体的产品
 * @see zephyr.factorymethod.idcard.IDCard
 * ConcreteCreator：具体的创建者
 * @see zephyr.factorymethod.idcard.IDCardFactory
 *
 * 不用new关键字生成实例，防止父类子类（接口实现类）的耦合
 * 使用设计模式设计类时，需要正确传达设计这些设计模式的意图
 *
 * 相关设计模式：
 * Template Method
 * Singleton
 * Composite
 * Iterator - 在迭代器模式生成迭代器时可以使用工厂方法模式
 *
 */
@Slf4j
public class FactoryMethodDemo {
    public static void main(String[] args) {
        IDCardFactory factory = new IDCardFactory();
        Product card1 = factory.create("小明");
        Product card2 = factory.create("小红");
        Product card3 = factory.create("小刚");
        card1.use();
        card2.use();
        card3.use();
        log.info(factory.getIdOwnerMap().toString());
    }
}
