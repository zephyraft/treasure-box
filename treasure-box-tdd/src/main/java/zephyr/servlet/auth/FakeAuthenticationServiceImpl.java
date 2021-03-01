package zephyr.servlet.auth;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zephyr on 2019-09-30.
 */
@Service
public class FakeAuthenticationServiceImpl implements AuthenticationService {

    private Map<String, String> users = new HashMap<>();

    public void addUser(String username, String password) {
        users.put(username, password);
    }

    @Override
    public boolean isValidLogin(String username, String password) {
        return users.containsKey(username) && password.equals(users.get(username));
    }
}
