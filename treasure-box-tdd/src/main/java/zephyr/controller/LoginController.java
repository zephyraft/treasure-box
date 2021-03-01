package zephyr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import zephyr.servlet.auth.AuthenticationService;

/**
 * Created by zephyr on 2019-09-30.
 */
@RestController
public class LoginController {

    private final AuthenticationService authenticator;

    public LoginController(AuthenticationService authenticator) {
        this.authenticator = authenticator;
    }

    @GetMapping("/login")
    public String login() {
        return "{}";
    }
}
