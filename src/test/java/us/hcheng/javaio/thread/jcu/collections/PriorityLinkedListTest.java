package us.hcheng.javaio.thread.jcu.collections;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import us.hcheng.javaio.thread.jcu.collections.custom.PriorityLinkedList;

import static org.junit.jupiter.api.Assertions.*;

class PriorityLinkedListTest {

    private static PriorityLinkedList<Integer> list;

    @BeforeAll
    static void init() {
        list = PriorityLinkedList.of(1,2,3,4,5,7,6,5,5,9);
    }

    @Test
    void test() {
        assertEquals(10, list.size());

        assertEquals(1, list.removeFirst());
        assertEquals(9, list.size());

        list.addFirst(-1);
        list.addFirst(100);
        assertEquals(11, list.size());
        assertTrue(list.contains(100));

        assertEquals(-1, list.removeFirst());
    }

}
