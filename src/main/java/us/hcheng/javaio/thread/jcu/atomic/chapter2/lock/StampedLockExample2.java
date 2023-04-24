package us.hcheng.javaio.thread.jcu.atomic.chapter2.lock;

import us.hcheng.javaio.thread.part2.chapter17.util.SleepUtil;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.IntStream;

public class StampedLockExample2 {
    private static final StampedLock LOCK = new StampedLock();
    private static final LinkedList<Long> DATA = new LinkedList<>();

    private static long writeCount = 0;
    private static long readCount = 0;


    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);

        IntStream.range(0, 5).forEach(i -> {
            new Thread(() -> {
                service.submit(() -> {
                    while (true)
                        read();
                });
            }).start();
        });

        IntStream.range(0, 5).forEach(i -> {
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
        long stamp = LOCK.tryOptimisticRead();
        if (!LOCK.validate(stamp)) {
            try {
                stamp = LOCK.readLock();
                //System.err.println(stamp + " === " + t.getName() + " READ: " + DATA + " SIZE: " + DATA.size());
                //SleepUtil.sleep(1000);
                readCount++;
            } finally {
                LOCK.unlockRead(stamp);
            }
        } else {
            readCount++;
        }
    }

    private static void write() {
        Thread t = Thread.currentThread();
        long stamp = LOCK.writeLock();
        try {
            long data = System.currentTimeMillis();
            //System.out.println(t.getName()+ " WRITE: " + data + " SIZE: " + DATA.size());
            DATA.addLast(data);
            //SleepUtil.sleep(3000);
            writeCount++;
        } finally {
            LOCK.unlockWrite(stamp);
        }
    }

}
