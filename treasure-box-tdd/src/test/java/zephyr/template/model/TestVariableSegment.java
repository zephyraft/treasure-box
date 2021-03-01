package zephyr.template.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import zephyr.template.exception.MissingValueException;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Created by zephyr on 2019-09-29.
 */
class TestVariableSegment {

    private Map<String, String> variables;

    @BeforeEach
    void setUp() {
        variables = new HashMap<>();
    }

    @Test
    void variableEvaluatesAsItsValue() {
        String name = "myvar";
        String value = "myvalue";
        variables.put(name, value);
        assertEquals(value, new Variable(name).evaluate(variables));
    }

    @Test
    void missingVariableRaisesException() {
        String name = "myvar";
        try {
            new Variable(name).evaluate(variables);
            fail("Missing variable value should raise an exception");
        } catch (MissingValueException expected) {
            assertEquals("No value for ${" + name + "}", expected.getMessage());
        }
    }
}
