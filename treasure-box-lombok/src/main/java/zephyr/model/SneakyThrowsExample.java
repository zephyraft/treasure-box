package zephyr.model;

import lombok.SneakyThrows;

import java.io.UnsupportedEncodingException;

/**
 * Created by zephyr on 2019-06-30.
 */
public class SneakyThrowsExample implements Runnable {
    @SneakyThrows(UnsupportedEncodingException.class)
    public String utf8ToString(byte[] bytes) {
        return new String(bytes, "UTF-8");
    }

    @SneakyThrows
    public void run() {
        throw new Throwable();
    }
}
