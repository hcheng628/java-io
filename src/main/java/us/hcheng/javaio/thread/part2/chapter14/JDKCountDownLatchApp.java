package us.hcheng.javaio.thread.part2.chapter14;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

public class JDKCountDownLatchApp {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(5);

        System.out.println("******  1ST Phase ******");
        IntStream.range(0, 5).forEach(i -> {
            new Thread(() -> {
                System.out.println("Doing ... 1ST ... " + Thread.currentThread());
                latch.countDown();
            }, String.join("-", "T", String.valueOf(i))).start();
        });

        latch.await();
        System.out.println("******  2ND Phase ******");
        IntStream.range(0, 5).forEach(i -> {
            new Thread(() -> {
                System.out.println("Doing ... 2ND ... " + Thread.currentThread());
            }, String.join("-", "T", String.valueOf(i))).start();
        });
    }

}
