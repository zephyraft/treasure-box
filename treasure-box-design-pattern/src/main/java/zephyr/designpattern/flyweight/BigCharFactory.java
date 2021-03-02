package zephyr.designpattern.flyweight;

import java.util.HashMap;

public class BigCharFactory {
    private static final BigCharFactory singleton = new BigCharFactory();
    private final HashMap<String, BigChar> pool = new HashMap<>();
    private BigCharFactory() {
    }
    public static BigCharFactory getInstance() {
        return singleton;
    }
    public synchronized BigChar getBigChar(char charName) {
        BigChar bigChar = pool.get("" + charName);
        if (bigChar == null) {
            bigChar = new BigChar(charName);
            pool.put("" + charName, bigChar);
        }
        return bigChar;
    }
}
