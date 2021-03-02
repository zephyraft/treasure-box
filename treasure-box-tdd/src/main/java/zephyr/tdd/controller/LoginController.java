package zephyr.tdd.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import zephyr.tdd.servlet.auth.AuthenticationService;


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
