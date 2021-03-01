package zephyr.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * Created by zephyr on 2020/6/1.
 */
@Slf4j
public class SleepUtils {

    private SleepUtils() {
    }

    @SneakyThrows
    public static void second(long seconds){
        TimeUnit.SECONDS.sleep(seconds);
    }

    @SneakyThrows
    public static void mill(long mills){
        TimeUnit.MILLISECONDS.sleep(mills);
    }
}
