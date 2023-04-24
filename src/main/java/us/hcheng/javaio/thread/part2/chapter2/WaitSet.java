package us.hcheng.javaio.thread.part2.chapter2;

import java.util.stream.IntStream;

public class WaitSet {

    private static final Object LOCK = new Object();

    public static void main(String[] args) {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            new Thread(() -> {
                try {
                    synchronized (LOCK) {
                        System.out.println(Thread.currentThread() + " LOCK.wait()");
                        LOCK.wait();
                        System.out.println(Thread.currentThread() + " WAKES UP!");
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }, String.join("-", "Thread", String.valueOf(i))).start();
        });

        try {
            Thread.sleep(3_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        IntStream.rangeClosed(1, 10).forEach(i -> {
//            synchronized (LOCK) {
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                LOCK.notify();
//            }
//        });

        synchronized (LOCK) {
            LOCK.notifyAll();
        }


        new Thread(() -> {
            synchronized (LOCK) {
                try {
                    System.out.println("im in the sync block... about to sleep");
                    LOCK.wait();
                    System.out.println("someone wakes me up");
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();

        try {
            Thread.sleep(3_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (LOCK) {
            LOCK.notify();
        }
    }

}
