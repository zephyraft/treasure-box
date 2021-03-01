package zephyr.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zephyr on 2018/12/6.
 */
public class ModelFactory {

    private static final String CAMBRIDGE = "Cambridge";
    private static final String MILAN = "Milan";

    private static final Trader raoul = new Trader("Raoul", CAMBRIDGE);
    private static final Trader mario = new Trader("Mario", MILAN);
    private static final Trader alan = new Trader("Alan", CAMBRIDGE);
    private static final Trader brian = new Trader("Brian", CAMBRIDGE);

    private static final List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
    );

    private static final List<Dish> menu = Arrays.asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef", false, 700, Dish.Type.MEAT),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("season fruit", true, 120, Dish.Type.OTHER),
            new Dish("pizza", true, 550, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("salmon", false, 450, Dish.Type.FISH)
    );

    private ModelFactory() {
    }

    public static List<Dish> getMenu() {
        return new ArrayList<>(menu);
    }

    public static List<Transaction> getTransaction() {
        return new ArrayList<>(transactions);
    }

}
