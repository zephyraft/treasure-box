package zephyr.designpattern.templatemethod;

/**
 * 模板方法模式 - 在父类中定义处理流程的框架，在子类中实现具体处理
 *
 * 模板方法模式 - 2个角色
 * AbstractClass：抽象类（接口），负责实现模板方法，并声明在模板方法中使用到的抽象方法
 * @see zephyr.designpattern.templatemethod.AbstractDisplay
 * ConcreteClass：具体类，负责实现AbstractClass定义的抽象方法
 * @see zephyr.designpattern.templatemethod.CharDisplay
 * @see zephyr.designpattern.templatemethod.StringDisplay
 *
 * 可以使逻辑处理通用化
 * 父类子类（接口实现类）之间联系紧密，共同工作，编写子类必须充分理解父类
 * 父类子类一致性（里氏替换原则，LSP）
 *
 * 相关设计模式：
 * Factory Method
 * Strategy
 */
public class TemplateMethodDemo {
    public static void main(String[] args) {
        AbstractDisplay d1 = new CharDisplay('H');
        d1.display();

        AbstractDisplay d2 = new StringDisplay("Hello, Wolrd!");
        d2.display();
    }
}
