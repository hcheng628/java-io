package us.hcheng.javaio.thread.jcu.atomic.chapter2.phaser;

import us.hcheng.javaio.thread.part2.chapter17.util.SleepUtil;

import java.util.concurrent.Phaser;
import java.util.stream.IntStream;

public class PhaserExample3 {

    public static void main(String[] args) {
        Phaser phaser = new Phaser(2);
        phaser.bulkRegister(10);

        IntStream.range(0, 2).forEach(i -> {
            new Thread(() -> {
                phaserInfo(phaser, true, "1");
                phaser.arriveAndAwaitAdvance();
                phaserInfo(phaser, false, "1");

                phaserInfo(phaser, true, "2");
                phaser.arriveAndAwaitAdvance();
                phaserInfo(phaser, false, "2");
                System.err.println(Thread.currentThread() + " Done!");
            }).start();
        });

        /**
         * There are 2 arriveAndAwaitAdvance, so we need
         * 2 sets of 10 see below to hack this bulkRegister
         */

        IntStream.range(0, 10).forEach(i -> {
            new Thread(() -> {
                System.out.println(i);
                phaser.arriveAndAwaitAdvance();
            }).start();
        });

        SleepUtil.sleep(3000);
        IntStream.range(0, 10).forEach(i -> {
            new Thread(() -> {
                System.out.println(i);
                phaser.arriveAndAwaitAdvance();
            }).start();
        });
    }

    private static void phaserInfo(Phaser phaser, boolean before, String tag) {
        StringBuilder sb = new StringBuilder();
        sb.append("**********" + Thread.currentThread() + "********** before: " + before + " === " + tag);
        sb.append("\nPhase: ").append(phaser.getPhase());
        sb.append("\nRegistered Parties: ").append(phaser.getRegisteredParties());
        sb.append("\nArrived Parties: ").append(phaser.getArrivedParties());
        sb.append("\nUnarrived Parties: ").append(phaser.getUnarrivedParties());
        sb.append("\n");
        System.out.println(sb);
    }

}
