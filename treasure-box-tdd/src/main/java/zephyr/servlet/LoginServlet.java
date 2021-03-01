package zephyr.servlet;

import lombok.extern.slf4j.Slf4j;
import zephyr.servlet.auth.AuthenticationService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zephyr on 2019-09-30.
 */
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
