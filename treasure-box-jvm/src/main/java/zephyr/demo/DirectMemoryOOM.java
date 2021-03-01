package zephyr.demo;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 直接内存溢出
 * VMArgs：-Xmx20m -XX:MaxDirectMemorySize=10M
 * Created by zephyr on 2020/5/27.
 */
public class DirectMemoryOOM {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) throws IllegalAccessException {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        while (true) {
            unsafe.allocateMemory(_1MB);
        }
    }

}
