package zephyr.designpattern.iterator.impl;

import zephyr.designpattern.iterator.Book;
import zephyr.designpattern.iterator.BookShelfAggregate;
import zephyr.designpattern.iterator.Iterator;

/**
 * 遍历书架的迭代器
 */
public class BookShelfIterator implements Iterator<Book> {

    private final BookShelfAggregate bookShelfAggregate;
    private int index;

    public BookShelfIterator(BookShelfAggregate bookShelfAggregate) {
        this.bookShelfAggregate = bookShelfAggregate;
        this.index = 0;
    }

    @Override
    public boolean hasNext() {
        return index < bookShelfAggregate.getLength();
    }

    @Override
    public Book next() {
        return bookShelfAggregate.getBookAt(index++);
    }
}
