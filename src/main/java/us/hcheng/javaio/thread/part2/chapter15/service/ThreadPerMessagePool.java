package us.hcheng.javaio.thread.part2.chapter15.service;

import us.hcheng.javaio.thread.part2.chapter15.entity.Message;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPerMessagePool {

    private final ExecutorService service;

    public ThreadPerMessagePool() {
        service = Executors.newFixedThreadPool(10);
    }

    public void execute(Message msg) {
        service.submit(() -> {
            while(!Thread.currentThread().isInterrupted()) {
                Thread t = Thread.currentThread();
                try {
                    System.err.println("Moka: t:" + t + " alive: " + t.isAlive() + " interrupted: " + t.isInterrupted());

                    if (1 == 2)
                        Thread.sleep(new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    System.err.println("BREAK Moka: " + e + " t:" + t + " alive: " + t.isAlive() + " interrupted: " + t.isInterrupted());
                    break;
                }
                System.out.println(msg);
            }

            if (Thread.currentThread().isInterrupted())
                System.out.println("*****" + Thread.currentThread() + "*****");
        });
    }

    public void close() {
        service.shutdownNow();
    }
}
