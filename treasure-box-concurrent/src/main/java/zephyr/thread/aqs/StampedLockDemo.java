package zephyr.thread.aqs;

import java.util.concurrent.locks.StampedLock;

/**
 * Created by zephyr on 2020/6/3.
 */
public class StampedLockDemo {
    private final StampedLock stampedLock = new StampedLock();
    private double x;
    private double y;

    // 写锁（排他锁）
    void move(double deltaX, double deltaY) {
        final long stamp = stampedLock.writeLock();
        try {
            x += deltaX;
            y += deltaY;
        } finally {
            stampedLock.unlockWrite(stamp);
        }
    }

    // 乐观读锁(tryOptimisticRead)
    double distanceFromOrigin() {
        long stamp = stampedLock.tryOptimisticRead();
        double currentX = x;
        double currentY = y;
        // 检查锁有没有被写线程排他性抢占
        if (!stampedLock.validate(stamp)) {
            // 如果被抢占则获取一个共享读锁
            stamp = stampedLock.readLock();
            try {
                currentX = x;
                currentY = y;
            } finally {
                stampedLock.unlockRead(stamp);
            }
        }
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }

    // 悲观读锁，并尝试晋升为写锁
    void moveIfAtOrigin(double newX, double newY) {
        // 可使用乐观读锁替换
        long stamp = stampedLock.readLock();
        try {
            // 如果当前点在原点则移动
            while (x == 0.0 && y == 0.0) {
                // 尝试将读锁晋升为写锁
                final long writeStamp = stampedLock.tryConvertToWriteLock(stamp);
                if (writeStamp != 0L) {
                    // 升级成功
                    stamp = writeStamp;
                    x = newX;
                    y = newY;
                    break;
                } else {
                    // 升级失败，释放读锁，显示获取写锁，然后循环重试
                    stampedLock.unlockRead(stamp);
                    stamp = stampedLock.writeLock();
                }
            }
        } finally {
            stampedLock.unlock(stamp);
        }
    }

}
