package zephyr.learning;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by zephyr on 2019-09-29.
 */
class TestRegexLearning {

    @Test
    void testFindStartAndEnd() {
        String haystack = "The needle shop sells needles";
        String regex = "(needle)";
        Matcher matcher = Pattern.compile(regex).matcher(haystack);
        assertTrue(matcher.find());
        assertEquals(4, matcher.start(), "Wrong start index of 1st match");
        assertEquals(10, matcher.end(), "Wrong end index of 1st match");

        assertTrue(matcher.find());
        assertEquals(22, matcher.start(), "Wrong start index of 2nd match");
        assertEquals(28, matcher.end(), "Wrong end index of 2nd match");

        assertFalse(matcher.find());
    }
}
