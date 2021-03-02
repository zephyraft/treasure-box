package zephyr.designpattern.prototype;

import lombok.extern.slf4j.Slf4j;
import zephyr.designpattern.prototype.framework.Product;

@Slf4j
public class UnderlinePen implements Product {

    private final char ulChar;

    public UnderlinePen(char ulChar) {
        this.ulChar = ulChar;
    }

    @Override
    public void use(String s) {
        log.info("{}", s);
        log.info("{}{}{}", ulChar, ulChar, ulChar);
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
