package zephyr.guava;

import com.google.common.base.Throwables;

import java.io.IOException;
import java.sql.SQLException;

public class ThrowableDemo {

    public static void main(String[] args) throws IOException, SQLException {
        try {
            // someMethodThatCouldThrowAnything();
        } catch (Throwable t) {
            Throwables.throwIfInstanceOf(t, IOException.class);
            Throwables.throwIfInstanceOf(t, SQLException.class);
            throw t;
        }
    }

}
