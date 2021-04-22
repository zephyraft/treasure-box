package zephyr.arrow.app.factory;

import zephyr.arrow.app.model.Address;
import zephyr.arrow.app.model.Person;

import java.util.concurrent.ThreadLocalRandom;

public class ModelRandomFactory {
    private static final String[] STREETS = new String[]{
            "Halloway",
            "Sunset Boulvard",
            "Wall Street",
            "Secret Passageway"
    };
    private static final String[] CITIES = new String[]{
            "Brussels",
            "Paris",
            "London",
            "Amsterdam"
    };
    private static final String[] FIRST_NAMES = new String[]{"John", "Jane", "Gerard", "Aubrey", "Amelia"};
    private static final String[] LAST_NAMES = new String[]{"Smith", "Parker", "Phillips", "Jones"};

    public static Address randomAddress() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return new Address(
                pickRandom(STREETS),
                random.nextInt(1, 3000),
                pickRandom(CITIES),
                random.nextInt(1000, 10000)
        );
    }

    public static Person randomPerson() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return new Person(
                pickRandom(FIRST_NAMES),
                pickRandom(LAST_NAMES),
                random.nextInt(0, 120),
                randomAddress()
        );
    }

    private static <T> T pickRandom(T[] options) {
        return options[ThreadLocalRandom.current().nextInt(0, options.length)];
    }
}
