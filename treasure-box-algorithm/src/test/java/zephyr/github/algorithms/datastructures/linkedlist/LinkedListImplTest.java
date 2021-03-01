package zephyr.github.algorithms.datastructures.linkedlist;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import zephyr.github.algorithms.datastructures.list.LinkedListImpl;

class LinkedListImplTest {

    private LinkedListImpl<Integer> emptyLinkedList = new LinkedListImpl<>();
    private LinkedListImpl<Integer> singleNodeLinkedList = new LinkedListImpl<>();
    private LinkedListImpl<Integer> multipleNodeLinkedList = new LinkedListImpl<>();

    @BeforeEach
    void setUp() {
        singleNodeLinkedList = singleNodeLinkedList.append(1);
        multipleNodeLinkedList = multipleNodeLinkedList.append(1).append(2).append(3);
    }

    @Test
    void append() {
        Assertions.assertEquals(emptyLinkedList.append(3), emptyLinkedList);
    }

    @Test
    void prepend() {
    }

    @Test
    void delete() {
    }

    @Test
    void find() {
    }

    @Test
    void deleteTail() {
    }

    @Test
    void deleteHead() {
    }

    @Test
    void fromArray() {
    }

    @Test
    void toArray() {
    }

    @Test
    void reverse() {
    }
}