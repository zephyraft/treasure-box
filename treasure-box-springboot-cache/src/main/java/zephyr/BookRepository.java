package zephyr;

import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository {
    Book getByIsbn(String isbn);
}
