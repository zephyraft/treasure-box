package zephyr.strategy;

public class Hand {

    public static final int HAND_VALUE_GUU = 0; // 石头
    public static final int HAND_VALUE_CHO = 1; // 剪刀
    public static final int HAND_VALUE_PAA = 2; // 布
    public static final Hand[] HANDS = {
            new Hand(HAND_VALUE_GUU),
            new Hand(HAND_VALUE_CHO),
            new Hand(HAND_VALUE_PAA),
    };

    private static final String[] NAMES = {
            "石头", "剪刀", "布"
    };

    private final int handValue;

    public Hand(int handValue) {
        this.handValue = handValue;
    }

    public static Hand getHand(int handValue) {
        return HANDS[handValue];
    }

    public boolean isStrongerThan(Hand h) {
        return fight(h) == 1;
    }

    public boolean isWeakerThan(Hand h) {
        return fight(h) == -1;
    }

    private int fight(Hand h) {
        if (this == h) {
            return 0;
        } else if ((this.handValue + 1) % 3 == h.handValue) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return NAMES[handValue];
    }
}
