package zephyr.adapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Banner {

    private String string;

    public Banner(String string) {
        this.string = string;
    }

    public void showWithParen() {
        log.info("({})", string);
    }

    public void showWithAster() {
        log.info("*{}*", string);
    }
}
