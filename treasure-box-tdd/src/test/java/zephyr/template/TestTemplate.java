package zephyr.template;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import zephyr.template.exception.MissingValueException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Created by zephyr on 2019-09-29.
 */
class TestTemplate {

    private Template template;

    @BeforeEach
    void setUp() {
        template = new Template("${one}, ${two}, ${three}");
        template.set("one", "1");
        template.set("two", "2");
        template.set("three", "3");
    }

    @Test
    void multipleVariables() {
        assertTemplateEvaluatesTo("1, 2, 3");
    }

    @Test
    void unknownVariablesAreIgnored() {
        template.set("doesnotexist", "Hi");
        assertTemplateEvaluatesTo("1, 2, 3");
    }

    @Test
    void missingValueRaisesException() {
        try {
            new Template("${foo}").evaluate();
            fail("evaluate() should throw an exception if a variable was left without a value!");
        } catch (MissingValueException expected) {
            assertEquals("No value for ${foo}", expected.getMessage());
        }

    }

    @Test
    void variablesGetProcessedJustOnce() {
        template.set("one", "${one}");
        template.set("two", "${three}");
        template.set("three", "${two}");
        assertTemplateEvaluatesTo("${one}, ${three}, ${two}");
    }

    private void assertTemplateEvaluatesTo(String expected) {
        assertEquals(expected, template.evaluate());
    }
}
