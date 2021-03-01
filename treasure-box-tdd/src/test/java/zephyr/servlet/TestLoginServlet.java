package zephyr.servlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import zephyr.servlet.auth.AuthenticationService;
import zephyr.servlet.auth.FakeAuthenticationServiceImpl;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by zephyr on 2019-09-30.
 */
class TestLoginServlet {

    private static final String VALID_USERNAME = "validuser";
    private static final String CORRECT_PASSWORD = "correctpassword";

    private LoginServlet servlet;
    private FakeAuthenticationServiceImpl authenticator;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        authenticator = new FakeAuthenticationServiceImpl();
        authenticator.addUser(VALID_USERNAME, CORRECT_PASSWORD);
        servlet = new LoginServlet() {
            @Override
            protected AuthenticationService getAuthenticationService() {
                return authenticator;
            }
        };
        request = new MockHttpServletRequest(HttpMethod.GET.name(), "/login");
        response = new MockHttpServletResponse();
    }


    @Test
    void wrongPasswordShouldRedirectToErrorPage() throws ServletException, IOException {
        request.addParameter(LoginServlet.PARAM_USERNAME, "nosuchuser");
        request.addParameter(LoginServlet.PARAM_PASSWORD, "wrongpassword");
        servlet.service(request, response);
        assertEquals("/invalidlogin", response.getRedirectedUrl());
    }

    @Test
    void validLoginForwardsToFrontPageAndStoresUsername() throws ServletException, IOException {
        request.addParameter(LoginServlet.PARAM_USERNAME, VALID_USERNAME);
        request.addParameter(LoginServlet.PARAM_PASSWORD, CORRECT_PASSWORD);
        servlet.service(request, response);
        assertEquals("/frontpage", response.getRedirectedUrl());
        assertEquals("validuser", Objects.requireNonNull(request.getSession()).getAttribute("username"));
    }

}
