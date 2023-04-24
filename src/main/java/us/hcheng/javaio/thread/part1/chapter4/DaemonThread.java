package us.hcheng.javaio.thread.part1.chapter4;

import org.junit.rules.ExternalResource;

public class DaemonThread {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName() + " STARTED");
                    Thread.sleep(2_000);
                    System.out.println(Thread.currentThread().getName() + " ENDED");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        t.setDaemon(true);
        t.start();

        Thread.sleep(1_000);
        System.out.println(Thread.currentThread().getName() + " ENDED");

        System.err.println(Thread.currentThread().getThreadGroup().activeCount());;
    }

}
