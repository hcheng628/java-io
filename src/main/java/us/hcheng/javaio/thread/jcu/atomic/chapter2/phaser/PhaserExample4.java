package us.hcheng.javaio.thread.jcu.atomic.chapter2.phaser;

import us.hcheng.javaio.utils.SleepUtil;

import java.util.concurrent.Phaser;

public class PhaserExample4 {

    private static boolean noWait = false;

    public static void main(String[] args) {
        Phaser phaser = new Phaser(2) {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                return noWait;
            }
        };

        new Thread(()-> {
            journey(phaser);
        }, "T1").start();

        new Thread(() -> {
            journey(phaser);
        }, "T2").start();

        SleepUtil.sleep(5000);
        noWait = true;
    }


    private static void journey(Phaser phaser) {
        Thread t = Thread.currentThread();
        System.out.println("高一");
        phaser.arriveAndAwaitAdvance();

        System.out.println("高二");
        phaser.arriveAndAwaitAdvance();

        System.out.println("高三");
        phaser.arriveAndAwaitAdvance();

        System.out.println(t.getName() + " Before Nap: isTerminated: " + phaser.isTerminated() + " noWait: " + noWait);
        SleepUtil.sleep(10000);
        System.out.println(t.getName() + " After Nap: isTerminated: " + phaser.isTerminated() + " noWait: " + noWait);
        /**
         * arriveAndAwaitAdvance will not wait on each other
         * so isTerminated is true aftermath
         */

        System.out.println("大一");
        phaser.arriveAndAwaitAdvance();

        System.out.println("大二");
        phaser.arriveAndAwaitAdvance();

        System.out.println("大三");
        phaser.arriveAndAwaitAdvance();

        System.out.println("大四");
        System.out.println(t.getName() + " SEMI-FINAL Nap: isTerminated: " + phaser.isTerminated() + " noWait: " + noWait);
        phaser.arriveAndAwaitAdvance();

        System.out.println(t.getName() + " FINAL Nap: isTerminated: " + phaser.isTerminated() + " noWait: " + noWait);
    }

}
