package zephyr.demo.repository;

/**
 * Created by zephyr on 2019/12/17.
 */
public interface BlockingRepository<T> {

    void save(T value);

    T findFirst();

    Iterable<T> findAll();

    T findById(String id);
}
