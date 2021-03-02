package zephyr.designpattern.prototype;

import zephyr.designpattern.prototype.framework.Manager;
import zephyr.designpattern.prototype.framework.Product;

/**
 * 原型模式 - 不指定类名的前提下生成实例，根据现有实例来生成新的实例
 * 不采用new
 *
 * 原型模式 - 3个角色
 * Prototype：原型，负责定义用于复制现有实例来生成新实例的方法
 * @see zephyr.designpattern.prototype.framework.Product
 * ConcretePrototype：具体的原型
 * @see zephyr.designpattern.prototype.MessageBox
 * @see zephyr.designpattern.prototype.UnderlinePen
 * Client：使用者，负责使用复制实例的方法生成新的实例
 * @see zephyr.designpattern.prototype.framework.Manager
 *
 * 一旦在代码中出现要使用的类名，就无法与该类分离开来，也就无法实现复用
 *
 * 相关设计模式：
 * Flyweight
 * Memento
 * Composite & Decorator
 * Command
 *
 */
public class PrototypeDemo {

    public static final String MESSAGE = "Hello, World.";

    public static void main(String[] args) {
        // 准备
        Manager manager = new Manager();
        UnderlinePen upen = new UnderlinePen('~');
        MessageBox mbox = new MessageBox('*');
        MessageBox sbox = new MessageBox('/');
        manager.register("strong message", upen);
        manager.register("warning box", mbox);
        manager.register("slash box", sbox);

        // 生成
        Product p1 = manager.create("strong message");
        p1.use(MESSAGE);
        Product p2 = manager.create("warning box");
        p2.use(MESSAGE);
        Product p3 = manager.create("slash box");
        p3.use(MESSAGE);
    }

}
