package us.hcheng.javaio.thread.jcu.atomic.chapter1;

import sun.misc.Unsafe;
import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class UnsafeTest {

    public static void main(String[] args) {
        int poolSize = 1000;
        counterTest(Executors.newFixedThreadPool(poolSize), poolSize, new StupidCounter());
        counterTest(Executors.newFixedThreadPool(poolSize), poolSize, new SyncCounter());
        counterTest(Executors.newFixedThreadPool(poolSize), poolSize, new AtomicCounter());
        counterTest(Executors.newFixedThreadPool(poolSize), poolSize, new LockCounter());
        counterTest(Executors.newFixedThreadPool(poolSize), poolSize, new CASCounter());
    }

    public static void counterTest(ExecutorService service, int poolSize, Counter counter) {
        long start = System.currentTimeMillis();
        IntStream.range(0, poolSize).forEach(i -> {
            service.submit(new CounterRunnable(counter, 1000));
        });

        service.shutdown();
        try {
            service.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long duration = System.currentTimeMillis() - start;
        System.out.println("duration: " + duration);
        System.out.println("count: " + counter.getCount());
    }

    public static Unsafe getUnsafe() {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            return (Unsafe) f.get(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static class CounterRunnable implements Runnable {
        private final Counter counter;
        private final int repetitions;

        public CounterRunnable(Counter counter, int repetitions) {
            this.counter = counter;
            this.repetitions = repetitions;
        }

        @Override
        public void run() {
            IntStream.range(0, repetitions).forEach(i -> {
                counter.increment();
            });
        }
    }

    interface Counter {
        void increment();
        long getCount();
    }

    static class StupidCounter implements Counter {

        private int i;

        @Override
        public void increment() {
            i++;
        }

        @Override
        public long getCount() {
            return i;
        }
    }

    static class SyncCounter implements Counter {

        private int i;

        @Override
        public synchronized void increment() {
            i++;
        }

        @Override
        public long getCount() {
            return i;
        }
    }

    static class AtomicCounter implements Counter {
        private AtomicInteger atomicInteger = new AtomicInteger();

        @Override
        public void increment() {
            atomicInteger.incrementAndGet();
        }

        @Override
        public long getCount() {
            return atomicInteger.get();
        }
    }

    static class LockCounter implements Counter {
        private ReentrantLock lock = new ReentrantLock();
        private int i;

        @Override
        public void increment() {
            try {
                lock.lock();
                i++;
            } finally {
                lock.unlock();
            }
        }

        @Override
        public long getCount() {
            return i;
        }
    }


    static class CASCounter implements Counter {

        private long count;
        private final Unsafe unsafe;
        private final long offset;

        public CASCounter() {
            unsafe = getUnsafe();
            try {
                offset = unsafe.objectFieldOffset(CASCounter.class.getDeclaredField("count"));
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void increment() {
            while (true) {
                long curr = count;
                if (unsafe.compareAndSwapLong(this, offset, curr, curr + 1))
                    return;
            }
        }

        @Override
        public long getCount() {
            return count;
        }

    }

}
