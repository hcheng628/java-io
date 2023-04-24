package us.hcheng.javaio.thread.jcu.atomic.chapter1;

import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicBoolean;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AtomicBooleanTest {


    @Test
    void testCreateWithNoArg() {
        AtomicBoolean atomicBoolean = new AtomicBoolean();
        assertFalse(atomicBoolean.get());
    }

    @Test
    void testCreateWithArg() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        assertTrue(atomicBoolean.get());
    }

    @Test
    void testGetAndSet() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        boolean res = atomicBoolean.getAndSet(false);
        assertTrue(res);
        assertFalse(atomicBoolean.get());
    }

    @Test
    void testCompareAndSet() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        boolean res = atomicBoolean.compareAndSet(true, false);
        assertTrue(res);
        assertFalse(atomicBoolean.get());
    }

    @Test
    void testCompareAndSetFail() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        boolean res = atomicBoolean.compareAndSet(false, false);
        assertFalse(res);
        assertTrue(atomicBoolean.get());
    }

}
