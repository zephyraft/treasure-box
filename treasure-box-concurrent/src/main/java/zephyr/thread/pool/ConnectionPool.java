package zephyr.thread.pool;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * 简单连接池
 * Created by zephyr on 2020/6/2.
 */
public class ConnectionPool {

    private final LinkedList<Connection> pool = new LinkedList<>();

    public ConnectionPool(int initialSize) {
        if (initialSize > 0) {
            for (int i = 0; i < initialSize; i++) {
                pool.addLast(ConnectionDriver.createConnection());
            }
        }
    }

    public void releaseConnection(Connection connection) {
        if (connection != null) {
            synchronized (pool) {
                // 连接释放后需要进行通知，这样其他消费者能够感知到连接池中已经归还了一个连接
                pool.addLast(connection);
                pool.notifyAll();
            }
        }
    }

    @SneakyThrows
    public Connection fetchConnection(long mills) {
        synchronized (pool) {
            if (mills <= 0) {
                while (pool.isEmpty()) {
                    pool.wait(); // 等待并释放锁
                }
                return pool.removeFirst(); // 获取队列第一个
            } else {
                long future = System.currentTimeMillis() + mills; // 超时时间
                long remaining = mills; // 剩余时间
                while (pool.isEmpty() && remaining > 0) {
                    pool.wait(remaining); // 超时等待
                    remaining = future - System.currentTimeMillis(); // 更新剩余时间
                }
                Connection result = null;
                if (!pool.isEmpty()) {
                    result = pool.removeFirst();
                }
                return result;
            }
        }
    }

}
