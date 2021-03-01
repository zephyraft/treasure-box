package zephyr.template;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by zephyr on 2019-09-29.
 */
class TestTemplatePerformance {

    // Omitted the setUp() for creating a 100-word template
    // with 20 variables and populating it with approximately
    // 15-character values
    private Template template;

    @BeforeEach
    void setUp() {
        template = new Template("");
    }


    @Test
    void templateWith100WordsAnd20Variables() {
        long expected = 200L;
        long time = System.currentTimeMillis();
        template.evaluate();
        time = System.currentTimeMillis() - time;
        assertTrue(time <= expected, "Rendering the template took " + time + "ms while the target was " + expected + "ms");
    }
}
