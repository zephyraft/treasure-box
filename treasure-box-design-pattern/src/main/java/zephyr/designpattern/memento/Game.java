package zephyr.designpattern.memento;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    public static final String GOOD_FRUIT_PREFIX = " 好吃的 ";
    private static String[] fruitsName = {" 苹果 ", " 葡萄 ", " 香蕉 ", " 橘子 "};
    private final Random random = new Random();
    private int money;
    private List<String> fruits = new ArrayList<>();

    public Game(int money) {
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    public void bet() {
        int dice = random.nextInt(6) + 1;
        if (dice == 1) {
            money += 100;
            System.out.println("金钱增加");
        } else if (dice == 2) {
            money /= 2;
            System.out.println("金钱减半");
        } else if (dice == 6) {
            String f = getFruit();
            System.out.println("获得水果" + f);
            fruits.add(f);
        } else {
            System.out.println("什么都没发生");
        }
    }

    public Memento createMemento() {
        Memento memento = new Memento(money);
        for (String fruit : fruits) {
            if (fruit.startsWith(GOOD_FRUIT_PREFIX)) {
                memento.addFruit(fruit);
            }
        }
        return memento;
    }

    public void restoreMemento(Memento memento) {
        this.money = memento.getMoney();
        this.fruits = memento.getFruits();
    }

    @Override
    public String toString() {
        return "Game{" +
                "money=" + money +
                ", fruits=" + fruits +
                '}';
    }

    private String getFruit() {
        String prefix = "";
        if (random.nextBoolean()) {
            prefix = GOOD_FRUIT_PREFIX;
        }
        return prefix + fruitsName[random.nextInt(fruitsName.length)];
    }


}
