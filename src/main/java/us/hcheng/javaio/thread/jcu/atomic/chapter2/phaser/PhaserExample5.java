package us.hcheng.javaio.thread.jcu.atomic.chapter2.phaser;

import us.hcheng.javaio.thread.part2.chapter17.util.SleepUtil;
import java.util.concurrent.Phaser;
import java.util.stream.IntStream;

public class PhaserExample5 {

    public static void main(String[] args) {
        int size = 5;
        Phaser phaser = new Phaser(size);

        for (;;) {
            // left one for main thread
            IntStream.range(0, size - 1).forEach(i -> {
                new Thread(()->{
                    journey(phaser);
                }, "T" + (i + 1)).start();
            });

            // main waits for all kids's homework to complete
            phaser.arriveAndAwaitAdvance();
            System.out.println("All kids have completed their homework ;-)");
        }
   }


    private static void journey(Phaser phaser) {
        Thread t = Thread.currentThread();
        System.out.println(t.getName() + " Got home, doing some homework...");
        SleepUtil.sleep(2000);
        int val = phaser.arrive();  // returns phase #
        System.err.println(t.getName() + " i can play now: " + val);
    }

}
