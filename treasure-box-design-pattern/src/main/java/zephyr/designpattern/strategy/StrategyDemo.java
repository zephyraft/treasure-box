package zephyr.designpattern.strategy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StrategyDemo {

    public static void main(String[] args) {
        int seed1 = 314;
        int seed2 = 15;
        Player p1 = new Player("Taro", new WinningStrategy(seed1));
        Player p2 = new Player("Hana", new WinningStrategy(seed2));
        for (int i = 0; i < 10000; i++) {
            Hand nextHand1 = p1.nextHand();
            Hand nextHand2 = p2.nextHand();
            if (nextHand1.isStrongerThan(nextHand2)) {
                log.info("Winner:{}", p1);
                p1.win();
                p2.lose();
            } else if (nextHand1.isWeakerThan(nextHand2)) {
                log.info("Winner:{}", p2);
                p2.win();
                p1.lose();
            } else {
                log.info("Even...");
                p1.even();
                p2.even();
            }
        }
        log.info("Total result:");
        log.info(p1.toString());
        log.info(p2.toString());
    }

}
