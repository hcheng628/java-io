package us.hcheng.javaio.thread.jcu.atomic.chapter2.phaser;

import us.hcheng.javaio.utils.SleepUtil;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class PhaserExample7 {

    public static void main(String[] args) {
        final Phaser phaser = new Phaser(3);
        //nonInterruptedTest(phaser);
        //interruptedTest(phaser);
        interruptedTimeoutTest(phaser);
    }

    private static void nonInterruptedTest(Phaser phaser) {
        Thread nonInterrupt = new Thread(()->{
            phaser.awaitAdvance(phaser.getPhase());
        });
        nonInterrupt.start();
        SleepUtil.sleep(1000);

        System.out.println("isInterrupted(): " + nonInterrupt.isInterrupted());
        System.out.println("isAlive(): " + nonInterrupt.isAlive());
        nonInterrupt.interrupt();
        System.err.println("isInterrupted(): " + nonInterrupt.isInterrupted());
        System.err.println("isAlive(): " + nonInterrupt.isAlive());
    }

    private static void interruptedTest(Phaser phaser) {
        Thread nonInterrupt = new Thread(()->{
            try {
                phaser.awaitAdvanceInterruptibly(phaser.getPhase());
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        });

        nonInterrupt.start();
        SleepUtil.sleep(1000);
        System.out.println("isInterrupted(): " + nonInterrupt.isInterrupted());
        System.out.println("isAlive(): " + nonInterrupt.isAlive());
        nonInterrupt.interrupt();
        System.err.println("isInterrupted(): " + nonInterrupt.isInterrupted());
        System.err.println("isAlive(): " + nonInterrupt.isAlive());
    }

    private static void interruptedTimeoutTest(Phaser phaser) {
        Thread nonInterrupt = new Thread(()->{
            try {
                phaser.awaitAdvanceInterruptibly(phaser.getPhase(), 3, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                System.err.println(e);
            } catch (TimeoutException e) {
                System.out.println("*** isInterrupted(): " + Thread.currentThread().isInterrupted());
                System.out.println("*** isAlive(): " + Thread.currentThread().isAlive());
                throw new RuntimeException(e);
            }
        });
        nonInterrupt.start();
        System.out.println("isInterrupted(): " + nonInterrupt.isInterrupted());
        System.out.println("isAlive(): " + nonInterrupt.isAlive());
        System.err.println("isInterrupted(): " + nonInterrupt.isInterrupted());
        System.err.println("isAlive(): " + nonInterrupt.isAlive());
    }

}
