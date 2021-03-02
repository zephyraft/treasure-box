package zephyr.logback.transfer;

import java.security.SecureRandom;
import java.util.UUID;

public class TransactionFactory {

    SecureRandom random = new SecureRandom();

    public Transfer newInstance() {
        return new Transfer(UUID.randomUUID().toString(), generateString(10), Math.abs(random.nextLong()));
    }

    private String generateString(int length) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
