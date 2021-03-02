package zephyr.jvm;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 堆溢出
 * VMArgs：-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/Users/zephyr/Desktop
 */
@Slf4j
public class HeapOOM {

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();

        try {
            while (true) {
                list.add(new OOMObject());
            }
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
        }
    }

    private static class OOMObject {

    }

}
