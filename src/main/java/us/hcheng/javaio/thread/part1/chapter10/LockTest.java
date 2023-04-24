package us.hcheng.javaio.thread.part1.chapter10;

import java.util.stream.Stream;

public class LockTest {

    public static void main(String[] args) throws InterruptedException {
        final BooleanLock lock = new BooleanLock();
        Stream.of("T1", "T2", "T3", "T4").forEach(name -> {
            new Thread(() -> {
                try {
                    lock.lock(5000L);
                    System.out.println(Thread.currentThread().getName() + " have the lock Monitor.");
                    work();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Lock.MokaTimeoutException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }, name).start();
        });

        // 问题：违规非法操作。导致运行混乱
        /**/
        Thread.sleep(100);
        lock.unlock();
    }

    private static void work() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " is working...");
        Thread.sleep(10_000);
    }
}
