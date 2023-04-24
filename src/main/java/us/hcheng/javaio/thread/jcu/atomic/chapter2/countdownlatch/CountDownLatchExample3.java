package us.hcheng.javaio.thread.jcu.atomic.chapter2.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchExample3 {

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(1);
        Thread currentThread = Thread.currentThread();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // interrupt or countDown to move on latch.await(); on line 26
                latch.countDown();
                //currentThread.interrupt();
            }

        }).start();

        try {
            latch.await();
            //latch.await(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            System.err.println(ex);
        } finally {
            System.out.println("Done Latch Count: " + latch.getCount());
        }
    }

}
