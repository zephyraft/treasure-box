package zephyr.tdd.time;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class TestSystemTimeAbstraction {
    @Test
    void clockReturnsValidTimeInMilliseconds() {
        long before = System.currentTimeMillis();
        long clock = SystemTime.asMillis();
        long after = System.currentTimeMillis();
        assertBetween(before, clock, after);
    }

    @Test
    void clockReturnsFakedTimeInMilliseconds() {
        final long fakeTime = 123456790L;
        SystemTime.setTimeSource(() -> fakeTime);
        long clock = SystemTime.asMillis();
        assertEquals(fakeTime, clock);
    }

    @AfterEach
    void resetTimeSource() {
        SystemTime.reset();
    }

    private void assertBetween(long before, long actual, long after) {
        assertTrue(before <= actual && actual <= after);
    }
}
