package zephyr.iterator.impl;

import zephyr.iterator.Book;
import zephyr.iterator.BookShelfAggregate;
import zephyr.iterator.Iterator;

/**
 * 书架
 */
public class ArrayBookShelf implements BookShelfAggregate {

    private final Book[] books;
    private int last = 0;

    public ArrayBookShelf(int maxsize) {
        this.books = new Book[maxsize];
    }

    @Override
    public Book getBookAt(int index) {
        return books[index];
    }

    @Override
    public void appendBook(Book book) {
        this.books[last++] = book;
    }

    @Override
    public int getLength() {
        return last;
    }

    @Override
    public Iterator<Book> iterator() {
        return new BookShelfIterator(this);
    }
}
