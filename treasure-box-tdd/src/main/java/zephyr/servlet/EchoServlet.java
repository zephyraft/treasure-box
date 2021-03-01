package zephyr.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * Created by zephyr on 2019-09-30.
 */
@Slf4j
public class EchoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setHeader("Content-Type", "text/plain");
        try (PrintWriter writer = resp.getWriter()) {
            Enumeration<String> e = req.getParameterNames();
            while (e.hasMoreElements()) {
                String parameter = String.valueOf(e.nextElement());
                String[] values = req.getParameterValues(parameter);
                for (String value : values) {
                    writer.write(parameter + "=" + value);
                    writer.write("\n");
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

}
