package us.hcheng.javaio.thread.part1.chapter9;

import java.util.stream.IntStream;

public class DiffBetweenSleep_Wait {

    private static Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
        // m1();
        // m2();
        IntStream.range(1, 3).forEach(i->{
            new Thread(()->{
                //m3();
                m4();
            }).start();
        });

        Thread.sleep(1_000);

        synchronized (LOCK) {
            Thread.sleep(1_000);
            LOCK.notifyAll();
        }
    }

    public static void m1() {
        try {
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void m2() {
        synchronized (LOCK) {
            try {
                LOCK.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void m3() {
        synchronized (LOCK) {
            try {
                System.out.println("The Thread " + Thread.currentThread().getName() + " enter.");
                Thread.sleep(2_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void m4() {
        synchronized (LOCK) {
            try {
                System.out.println("The Thread " + Thread.currentThread().getName() + " enter.");
                LOCK.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("The Thread " + Thread.currentThread().getName() + " complete.");
        }
    }

}
