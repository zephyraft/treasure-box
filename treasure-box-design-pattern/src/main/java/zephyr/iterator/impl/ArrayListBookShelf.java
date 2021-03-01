package zephyr.iterator.impl;

import zephyr.iterator.Book;
import zephyr.iterator.BookShelfAggregate;
import zephyr.iterator.Iterator;

import java.util.ArrayList;
import java.util.List;

/**
 * 书架
 */
public class ArrayListBookShelf implements BookShelfAggregate {

    private final List<Book> books;

    public ArrayListBookShelf() {
        this.books = new ArrayList<>();
    }

    @Override
    public Book getBookAt(int index) {
        return books.get(index);
    }

    @Override
    public void appendBook(Book book) {
        books.add(book);
    }

    @Override
    public int getLength() {
        return books.size();
    }

    @Override
    public Iterator<Book> iterator() {
        return new BookShelfReverseIterator(this);
    }
}
