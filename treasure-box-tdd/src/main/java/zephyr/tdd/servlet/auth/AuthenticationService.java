package zephyr.tdd.servlet.auth;


public interface AuthenticationService {
    boolean isValidLogin(String username, String password);
}
