package us.hcheng.javaio.thread.jcu.atomic.chapter1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import us.hcheng.javaio.thread.jcu.atomic.chapter1.entity.TestMe;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AtomicIntegerFieldUpdaterTest {

    @Test
    void testDefaultAccessVolatileInt() {
        Throwable t = Assertions.assertThrows(RuntimeException.class, ()->{
            AtomicIntegerFieldUpdater<TestMe> updater = AtomicIntegerFieldUpdater.newUpdater(TestMe.class, "defVolatile");
            TestMe testMe = new TestMe();
            updater.compareAndSet(testMe, 0, 1);
        });

        assertTrue(t.getMessage().contains("IllegalAccessException"));
    }

    @Test
    void testPublicAccessNonVolatileInt() {
        Throwable t = Assertions.assertThrows(IllegalArgumentException.class, ()->{
            AtomicIntegerFieldUpdater<TestMe> updater = AtomicIntegerFieldUpdater.newUpdater(TestMe.class, "publicNonVolatile");
            TestMe testMe = new TestMe();
            updater.compareAndSet(testMe, 0, 1);
        });

        assertEquals("Must be volatile type", t.getMessage());
    }

    @Test
    void testPrivateAccessVolatileInt() {
        Assertions.assertThrows(RuntimeException.class, ()->{
            AtomicIntegerFieldUpdater<TestMe> updater = AtomicIntegerFieldUpdater.newUpdater(TestMe.class, "priVolatile");
            TestMe testMe = new TestMe();
            updater.compareAndSet(testMe, 0, 1);
        });
    }
    @Test
    void testPublicAccessVolatileInteger() {
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            AtomicIntegerFieldUpdater<TestMe> updater = AtomicIntegerFieldUpdater.newUpdater(TestMe.class, "pubVolatileInteger");
            updater.compareAndSet(null, 0, 1);
        });
    }

}
