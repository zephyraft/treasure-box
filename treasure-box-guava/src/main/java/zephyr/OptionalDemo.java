package zephyr;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import lombok.extern.slf4j.Slf4j;

/**
 * Optional 可替换为jdk的api
 * Created by zephyr on 2020/4/2.
 */
@Slf4j
public class OptionalDemo {

    public static void main(String[] args) {
        final Optional<Integer> possible = Optional.of(5);
        log.info("{}", possible.isPresent()); // returns true
        log.info("{}", possible.get()); // returns 5

        final Optional<Object> absent = Optional.absent();
        log.info("{}", absent.isPresent());

        final Object nonNullObject = MoreObjects.firstNonNull(null, new Object());
        log.info("{}", nonNullObject);
    }

}
