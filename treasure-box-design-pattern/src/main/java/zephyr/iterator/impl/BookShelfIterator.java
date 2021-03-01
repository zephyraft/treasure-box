package zephyr.iterator.impl;

import zephyr.iterator.Book;
import zephyr.iterator.BookShelfAggregate;
import zephyr.iterator.Iterator;

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
