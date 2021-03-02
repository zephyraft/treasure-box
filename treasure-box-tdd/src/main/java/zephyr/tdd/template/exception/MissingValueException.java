package zephyr.tdd.template.exception;


public class MissingValueException extends RuntimeException {
    public MissingValueException(String message) {
        super(message);
    }
}
