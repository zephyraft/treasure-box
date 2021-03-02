package zephyr.junit5;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;


@Slf4j
@DisplayName("A special test case")
public class StandardTests {

    @BeforeAll
    static void initAll() {
        log.info("initAll");
    }

    @AfterAll
    static void tearDownAll() {
        log.info("tearDownAll");
    }

    @BeforeEach
    void init() {
        log.info("init");
    }

    @Test
    void succeedingTest() {
        log.info("succeedingTest");
    }

    // @Test
    void failingTest() {
        log.info("failingTest");
        fail("a failing test");
    }

    @Test
    @Disabled("for demonstration purposes")
    void skippedTest() {
        log.info("skippedTest");
        // not executed
    }

    @Test
    void abortedTest() {
        log.info("abortedTest");
        assumeTrue("abc".contains("Z"));
        fail("test should have been aborted");
    }

    @AfterEach
    void tearDown() {
        log.info("tearDown");
    }

}
