package us.hcheng.javaio.thread.jcu.atomic.chapter2.phaser;

import us.hcheng.javaio.utils.SleepUtil;
import java.util.concurrent.Phaser;
import java.util.stream.IntStream;

public class PhaserExample1 extends Thread {
    private Phaser phaser;

    public PhaserExample1(Phaser phaser) {
        super();
        this.phaser = phaser;
        phaser.register();
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread() + " working...");
        SleepUtil.sleep();
        this.phaser.arriveAndAwaitAdvance();

        System.out.println(Thread.currentThread() + " complete...");
    }

    public static void main(String[] args) {
        Phaser phaser = new Phaser();

        IntStream.range(0, 5).forEach(i -> {
            new PhaserExample1(phaser).start();
        });

        new Thread(() -> {
            SleepUtil.sleep(3000);
            phaser.register();
            SleepUtil.sleep(5000);
            phaser.arriveAndAwaitAdvance();
            // phaser.arriveAndDeregister();
            System.out.println("Everyone has to wait for me!");
        }).start();

        phaser.register();
        SleepUtil.sleep(5000);
        phaser.arriveAndAwaitAdvance();
        System.out.println("Done...");
    }

}
