package zephyr.template.parse;

import org.junit.jupiter.api.Test;
import zephyr.template.model.PlainText;
import zephyr.template.model.Segment;
import zephyr.template.model.Variable;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by zephyr on 2019-09-29.
 */
class TestTemplateParse {

    @Test
    void parsingTemplateIntoSegmentObjects() {
        TemplateParse p = new TemplateParse();
        List<Segment> segments = p.parseSegments("a ${b} c ${d}");
        assertSegments(segments, new PlainText("a "), new Variable("b"), new PlainText(" c "), new Variable("d"));
    }

    private void assertSegments(List<Segment> actual, Segment... expected) {
        assertEquals(expected.length, actual.size(), "Number of segments doesn't match.");
        assertEquals(Arrays.asList(expected), actual);
    }

}
