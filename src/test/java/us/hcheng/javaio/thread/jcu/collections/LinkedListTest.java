package us.hcheng.javaio.thread.jcu.collections;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import us.hcheng.javaio.thread.jcu.collections.custom.LinkedList;
import static org.junit.jupiter.api.Assertions.*;

class LinkedListTest {

    private static LinkedList<String> list;

    @BeforeAll
    static void init() {
        list = LinkedList.of("Moka", "Cheng", "Apple", "logi", "Illy");
    }

    @Test
    void test() {
        assertEquals(5, list.size());
        assertFalse(list.contains("Pot"));
        assertTrue(list.contains("Illy"));

        assertEquals("Illy", list.removeFirst());
        assertFalse(list.contains("Illy"));

        assertEquals(4, list.size());

        list.addFirst("ThinkPad");
        list.addFirst("MacBook");

        assertEquals(6, list.size());
    }

}
