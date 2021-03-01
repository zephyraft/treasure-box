package zephyr.iterator;

public interface BookShelfAggregate extends Aggregate<Book> {

    Book getBookAt(int index);
    void appendBook(Book book);
    int getLength();

}
