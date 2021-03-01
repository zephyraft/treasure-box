package zephyr;

import com.google.common.base.Throwables;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by zephyr on 2020/4/3.
 */
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
