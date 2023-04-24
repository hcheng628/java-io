package us.hcheng.javaio.thread.jcu.atomic.chapter1;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

public class CompareAndSetLock {

    public static void main(String[] args) {
        Lock lock = new Lock();

        IntStream.range(1, 6).forEach(i -> {
            new Thread(() -> {
                try {
                    for (boolean didTask = false; !didTask; )
                        if (lock.lock()) {
                            doTask();
                            didTask = true;
                        }
                } finally {
                    lock.unlock();
                }
            }, i + "T").start();
        });
    }

    private static void doTask() {
        try {
            System.out.println(Thread.currentThread() + " is doing task!");
            Thread.sleep(1_000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

class Lock {
    private final AtomicBoolean atomicBoolean;
    private Thread currThread;

    public Lock() {
        atomicBoolean = new AtomicBoolean();
    }

    public boolean lock() {
        boolean ret = atomicBoolean.compareAndSet(false, true);

        if (ret)
            currThread = Thread.currentThread();

        return ret;
    }

    public void unlock() {
        if (!atomicBoolean.get() || currThread != Thread.currentThread())
            return;
        atomicBoolean.set(false);
    }
}