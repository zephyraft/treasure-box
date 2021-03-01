package zephyr.templatemethod;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CharDisplay implements AbstractDisplay {

    private final char ch;

    public CharDisplay(char ch) {
        this.ch = ch;
    }

    @Override
    public void open() {
        log.info("<<");
    }

    @Override
    public void print() {
        log.info("{}", ch);
    }

    @Override
    public void close() {
        log.info(">>");
    }
}
