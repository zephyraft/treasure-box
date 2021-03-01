package zephyr.prototype;

import lombok.extern.slf4j.Slf4j;
import zephyr.prototype.framework.Product;

@Slf4j
public class MessageBox implements Product {

    private final char decoChar;

    public MessageBox(char decoChar) {
        this.decoChar = decoChar;
    }

    @Override
    public void use(String s) {
        log.info("{} {} {}", decoChar, s, decoChar);
    }

    @Override
    public Product createClone() {
        try {
            return (Product) clone();
        } catch (CloneNotSupportedException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
