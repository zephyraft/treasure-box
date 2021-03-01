package zephyr.templatemethod;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringDisplay implements AbstractDisplay {

    private final String string;
    private final int width;

    public StringDisplay(String string) {
        this.string = string;
        this.width = string.getBytes().length;
    }

    @Override
    public void open() {
        printLine();
    }

    @Override
    public void print() {
        log.info("|{}|", string);
    }

    @Override
    public void close() {
        printLine();
    }

    private void printLine() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < width; i++) {
            s.append("-");
        }
        log.info("+{}+", s.toString());
    }
}
