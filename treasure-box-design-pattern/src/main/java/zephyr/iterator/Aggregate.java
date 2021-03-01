package zephyr.iterator;

/**
 * 表示集合的接口
 */
public interface Aggregate<T> {

    Iterator<T> iterator();

}
