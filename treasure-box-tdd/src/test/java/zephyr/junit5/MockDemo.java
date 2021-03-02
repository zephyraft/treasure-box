package zephyr.junit5;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;


@Slf4j
public class MockDemo {

    @Test
    void standardAssertions() {
        // mock creation
        List mockedList = mock(List.class);

        // using mock object - it does not throw any "unexpected interaction" exception
        mockedList.add("one");
        mockedList.clear();

        // selective, explicit, highly readable verification
        verify(mockedList).add("one");
        verify(mockedList).clear();

        // you can mock concrete classes, not only interfaces
        List mockedLinkedList = mock(LinkedList.class);

        // stubbing appears before the actual execution
        when(mockedLinkedList.get(0)).thenReturn("first");

        // the following prints "first"
        log.info("{}", mockedLinkedList.get(0));

        // the following prints "null" because get(999) was not stubbed
        log.info("{}", mockedLinkedList.get(999));
    }

}
