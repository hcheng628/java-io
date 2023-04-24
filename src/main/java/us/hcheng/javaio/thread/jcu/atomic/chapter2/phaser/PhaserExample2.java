package us.hcheng.javaio.thread.jcu.atomic.chapter2.phaser;

import java.util.concurrent.Phaser;
import java.util.stream.IntStream;

public class PhaserExample2 extends Thread {
    private Phaser phaser;

    public PhaserExample2(Phaser phaser) {
        super();
        this.phaser = phaser;
    }

    @Override
    public void run() {
        Thread t = currentThread();
        System.out.println(t + " 高一");
        phaser.arriveAndAwaitAdvance();
        System.out.println(t + " 高二");
        phaser.arriveAndAwaitAdvance();
        System.out.println(t + " 高三");
        phaser.arriveAndAwaitAdvance();
    }


    public static void main(String[] args) {
        int numOfKids = 6;
        Phaser phaser = new Phaser(numOfKids);
        IntStream.range(0, numOfKids).forEach(I -> new PhaserExample2(phaser).start());
    }
}
