package zephyr.servlet.auth;

/**
 * Created by zephyr on 2019-09-30.
 */
public interface AuthenticationService {
    boolean isValidLogin(String username, String password);
}
