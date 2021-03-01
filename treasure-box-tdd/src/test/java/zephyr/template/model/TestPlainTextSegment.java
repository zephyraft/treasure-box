package zephyr.template.model;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by zephyr on 2019-09-29.
 */
class TestPlainTextSegment {

    @Test
    void plainTextEvaluatesAsIs() {
        String text = "abc def";
        assertEquals(text, new PlainText(text).evaluate(Collections.emptyMap()));
    }

}
