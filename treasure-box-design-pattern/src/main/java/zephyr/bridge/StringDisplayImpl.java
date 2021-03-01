package zephyr.bridge;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringDisplayImpl extends DisplayImpl {

    private final String string;
    private final int width;

    public StringDisplayImpl(String string) {
        this.string = string;
        this.width = string.length();
    }

    @Override
    public void rawOpen() {
        printLine();
    }

    @Override
    public void rawPrint() {
        log.info("|{}|", string);
    }

    @Override
    public void rawClose() {
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
