package us.hcheng.javaio.thread.jcu.atomic.chapter1;

import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicLong;
import static org.junit.jupiter.api.Assertions.*;

public class AtomicLongTest {


    @Test
    void testCreateWithNoArg() {
        AtomicLong atomicLong = new AtomicLong();
        assertEquals(0L, atomicLong.get());
    }

    @Test
    void testCreateWithArg() {
        AtomicLong atomicLong = new AtomicLong(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, atomicLong.get());
    }

    @Test
    void testGetAndSet() {
        AtomicLong atomicLong = new AtomicLong(Integer.MAX_VALUE);
        long res = atomicLong.getAndSet(33);
        assertEquals(Integer.MAX_VALUE, res);
        assertEquals(33, atomicLong.get());
    }

    @Test
    void testCompareAndSet() {
        AtomicLong atomicLong = new AtomicLong(100);
        boolean res = atomicLong.compareAndSet(100, 200);
        assertTrue(res);
        assertEquals(200, atomicLong.get());
    }

    @Test
    void testCompareAndSetFail() {
        AtomicLong atomicLong = new AtomicLong(100);
        boolean res = atomicLong.compareAndSet(200, 300);
        assertFalse(res);
        assertEquals(100, atomicLong.get());
    }

}
