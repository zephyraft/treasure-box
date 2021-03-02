package zephyr.designpattern.iterator.impl;

import zephyr.designpattern.iterator.Book;
import zephyr.designpattern.iterator.BookShelfAggregate;
import zephyr.designpattern.iterator.Iterator;

/**
 * 遍历书架的迭代器
 * 从后向前
 */
public class BookShelfReverseIterator implements Iterator<Book> {

    private final BookShelfAggregate bookShelfAggregate;
    private int index;

    public BookShelfReverseIterator(BookShelfAggregate bookShelfAggregate) {
        this.bookShelfAggregate = bookShelfAggregate;
        this.index = bookShelfAggregate.getLength() - 1;
    }

    @Override
    public boolean hasNext() {
        return index >= 0;
    }

    @Override
    public Book next() {
        return bookShelfAggregate.getBookAt(index--);
    }
}
