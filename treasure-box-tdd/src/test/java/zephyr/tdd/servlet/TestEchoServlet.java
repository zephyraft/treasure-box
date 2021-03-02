package zephyr.tdd.servlet;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;


class TestEchoServlet {
    @Test
    void testEchoingParametersWithMultipleValues() throws UnsupportedEncodingException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addParameter("param1", "param1value1");
        request.addParameter("param2", "param2value1");
        request.addParameter("param2", "param2value2");

        new EchoServlet().doGet(request, response);
        String[] lines = response.getContentAsString().split("\n");
        assertEquals(3, lines.length, "Expected as many lines as we have parameter values");
        assertEquals("param1=param1value1", lines[0]);
        assertEquals("param2=param2value1", lines[1]);
        assertEquals("param2=param2value2", lines[2]);
    }
}
