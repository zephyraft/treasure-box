package zephyr.designpattern.iterator;

/**
 * 遍历集合的接口（迭代器接口）
 */
public interface Iterator<T> {

    boolean hasNext();

    /**
     * 返回当前元素，并指向下一个元素
     * @return 当前元素
     */
    T next();

}
