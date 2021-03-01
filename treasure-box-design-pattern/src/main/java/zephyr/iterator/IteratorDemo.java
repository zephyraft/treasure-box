package zephyr.iterator;

import lombok.extern.slf4j.Slf4j;
import zephyr.iterator.impl.ArrayBookShelf;
import zephyr.iterator.impl.ArrayListBookShelf;

/**
 * 迭代器模式 - 4个角色
 * Iterator：迭代器，负责定义按顺序逐个遍历元素的接口
 * @see zephyr.iterator.Iterator
 * ConcreteIterator： 具体的迭代器实现
 * @see zephyr.iterator.impl.BookShelfIterator
 * @see zephyr.iterator.impl.BookShelfReverseIterator
 * Aggregate：集合，负责定义创建迭代器角色的接口
 * @see zephyr.iterator.Aggregate
 * @see zephyr.iterator.BookShelfAggregate
 * ConcreteAggregate：具体的集合实现
 * @see zephyr.iterator.impl.ArrayBookShelf
 * @see zephyr.iterator.impl.ArrayListBookShelf
 *
 * 遍历和实现分离，遍历集合不依赖于集合的具体实现（无论是数组或Vector等等来实现）
 * 不要只使用具体类来编程，要优先使用抽象类和接口
 * 迭代器可以是简单地从前向后遍历，也可以是从后向前，或者跳跃式遍历
 *
 * 相关设计模式：
 * Visitor
 * Composite
 * Factory Method - 在迭代器模式生成迭代器时可以使用工厂方法模式
 */
@Slf4j
public class IteratorDemo {

    public static void main(String[] args) {
        log.info("采用数组实现书架");
        final ArrayBookShelf arrayBookShelf = new ArrayBookShelf(4);
        arrayBookShelf.appendBook(new Book("Around the World in 80 Days"));
        arrayBookShelf.appendBook(new Book("Bible"));
        arrayBookShelf.appendBook(new Book("Cinderella"));
        arrayBookShelf.appendBook(new Book("Daddy-Long-Legs"));

        final Iterator<Book> arrayBookShelfIterator = arrayBookShelf.iterator();
        while (arrayBookShelfIterator.hasNext()) {
            log.info(arrayBookShelfIterator.next().getName());
        }

        log.info("采用ArrayList实现书架，并返回的是逆向的迭代器");
        final ArrayListBookShelf arrayListBookShelf = new ArrayListBookShelf();
        arrayListBookShelf.appendBook(new Book("Bible"));
        arrayListBookShelf.appendBook(new Book("Cinderella"));

        final Iterator<Book> arrayListBookShelfIterator = arrayListBookShelf.iterator();
        while (arrayListBookShelfIterator.hasNext()) {
            log.info(arrayListBookShelfIterator.next().getName());
        }
    }

}
