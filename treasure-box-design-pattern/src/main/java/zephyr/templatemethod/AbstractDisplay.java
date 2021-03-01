package zephyr.templatemethod;

public interface AbstractDisplay {

    void open(); // 交给子类实现的抽象方法

    void print(); // 交给子类实现的抽象方法

    void close(); // 交给子类实现的抽象方法

    default void display() { // 模板方法，调用了抽象方法
        open();
        for (int i = 0; i < 5; i++) {
            print();
        }
        close();
    }

}
