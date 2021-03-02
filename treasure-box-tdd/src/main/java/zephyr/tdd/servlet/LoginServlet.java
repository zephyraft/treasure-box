package zephyr.tdd.servlet;

import lombok.extern.slf4j.Slf4j;
import zephyr.tdd.servlet.auth.AuthenticationService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
public class LoginServlet extends HttpServlet {

    public static final String PARAM_USERNAME = "j_username";
    public static final String PARAM_PASSWORD = "j_password";

    protected AuthenticationService getAuthenticationService() {
        return null;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String username = req.getParameter(PARAM_USERNAME);
        String password = req.getParameter(PARAM_PASSWORD);
        try {
            if (getAuthenticationService().isValidLogin(username, password)) {
                resp.sendRedirect("/frontpage");
                req.getSession().setAttribute("username", username);
            } else {
                resp.sendRedirect("/invalidlogin");
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
