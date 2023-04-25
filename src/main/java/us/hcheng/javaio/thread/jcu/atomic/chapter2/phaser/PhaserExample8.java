package us.hcheng.javaio.thread.jcu.atomic.chapter2.phaser;

import us.hcheng.javaio.utils.SleepUtil;

import java.util.concurrent.Phaser;
import java.util.stream.IntStream;

public class PhaserExample8 {

    public static void main(String[] args) {
        final int size = 6;
        final Phaser phaser = new Phaser(size);

        IntStream.range(0, size - 1).forEach(i -> {
            new Thread(() -> {
                Thread t = Thread.currentThread();
                System.out.println(t.getName() + " doing some work...");
                phaser.arriveAndAwaitAdvance();
                System.out.println(t.getName() + " just chilling out...");
            }).start();
        });

        System.out.println("Phaser isTerminated: " + phaser.isTerminated());
        SleepUtil.sleep();
        phaser.forceTermination();
        System.err.println("Phaser isTerminated: " + phaser.isTerminated());
    }

}
