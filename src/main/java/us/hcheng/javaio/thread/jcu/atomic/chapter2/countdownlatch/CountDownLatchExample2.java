package us.hcheng.javaio.thread.jcu.atomic.chapter2.countdownlatch;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample2 {

    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            try {
                Thread.sleep(500);
                System.out.println("T1 Ready!");
                latch.await();
                System.out.println("T1 GO!");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(() -> {
            try {
                System.out.println("T2 Ready!");
                latch.await();
                System.out.println("T2 GO!");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Command!");
            latch.countDown();
        }).start();


    }
}
