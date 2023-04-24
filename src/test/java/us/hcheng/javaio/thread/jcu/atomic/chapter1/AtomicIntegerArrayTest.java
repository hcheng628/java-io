package us.hcheng.javaio.thread.jcu.atomic.chapter1;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicIntegerArray;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AtomicIntegerArrayTest {

    @Test
    void testCreateTest() {
        AtomicIntegerArray array = new AtomicIntegerArray(10);
        assertEquals(10, array.length());
        array.set(1, 33);
        assertEquals(33, array.get(1));

        array = new AtomicIntegerArray(new int[]{1, 2, 3, 4, 5});
        assertEquals(5, array.length());
        // array = new AtomicIntegerArray(new Integer[]{1, 2, 3, 4, 5});
    }

    @Test
    void getAndSetTest() {
        AtomicIntegerArray array = new AtomicIntegerArray(10);
        array.getAndSet(3, 10);
        assertEquals(10, array.get(3));
    }

    @Test
    void incermentTest() {
        AtomicIntegerArray array = new AtomicIntegerArray(10);
        int res = array.incrementAndGet(1);
        assertEquals(1, res);
        assertEquals(res, array.get(1));
    }

}
