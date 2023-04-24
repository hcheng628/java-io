package us.hcheng.javaio.thread.jcu.atomic.chapter2.phaser;

import us.hcheng.javaio.thread.part2.chapter17.util.SleepUtil;

import java.util.concurrent.Phaser;
import java.util.stream.IntStream;

public class PhaserExample6 extends Thread {

    private final Phaser phaser;

    public PhaserExample6(Phaser phaser) {
        super();
        this.phaser = phaser;
        start();
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread() + " doing some work... " + phaser.getPhase());
        SleepUtil.sleep(3000);
        phaser.arrive();
        System.out.println(Thread.currentThread() + " done... " + phaser.getPhase());
    }

    public static void main(String[] args) {
        Phaser phaser = new Phaser(6);
        IntStream.range(0, 6).forEach(i -> new PhaserExample6(phaser));

        System.out.println("main phaser.getPhase(): " + phaser.getPhase());
        phaser.awaitAdvance(phaser.getPhase());
        System.out.println("All Done!");
    }

}
