package zephyr.template.exception;

/**
 * Created by zephyr on 2019-09-29.
 */
public class MissingValueException extends RuntimeException {
    public MissingValueException(String message) {
        super(message);
    }
}
