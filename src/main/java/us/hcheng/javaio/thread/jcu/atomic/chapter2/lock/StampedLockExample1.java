package us.hcheng.javaio.thread.jcu.atomic.chapter2.lock;

import us.hcheng.javaio.thread.part2.chapter17.util.SleepUtil;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.IntStream;

public class StampedLockExample1 {
    private static final StampedLock LOCK = new StampedLock();
    private static final LinkedList<Long> DATA = new LinkedList<>();
    private static long writeCount = 0;
    private static long readCount = 0;

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(200);

        IntStream.range(0, 99).forEach(i -> {
            new Thread(() -> {
                service.submit(() -> {
                    while (true)
                        read();
                });
            }).start();
        });

        IntStream.range(0, 99).forEach(i -> {
            new Thread(() -> {
                service.submit(() -> {
                    while (true)
                        write();
                });
            }).start();
        });

        while (true) {
            SleepUtil.sleep(3000);
            System.out.println("writeCount: " + writeCount + " readCount: " + readCount);
        }
    }

    private static void read() {
        Thread t = Thread.currentThread();
        try {
            LOCK.asReadLock().lock();
            //SleepUtil.sleep(100);
            //System.err.println(t.getName() + " READ: " + DATA.toString() + " SIZE: " + DATA.size());
            readCount++;
        } finally {
            LOCK.asReadLock().unlock();
        }
    }

    private static void write() {
        Thread t = Thread.currentThread();
        try {
            LOCK.asWriteLock().lock();
            //SleepUtil.sleep(500);
            long data = System.currentTimeMillis();
            //System.out.println(t.getName()+ " WRITE: " + data + " SIZE: " + DATA.size());
            DATA.addLast(data);
            writeCount++;
        } finally
        {
            LOCK.asWriteLock().unlock();
        }
    }

}
