package us.hcheng.javaio.thread.part1.chapter5;

import java.util.stream.IntStream;

public class ThreadJoin {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            IntStream.range(1, 1_000)
                    .forEach(i -> {
                        System.out.println(String.join(":",
                                Thread.currentThread().getName(), String.valueOf(i)));
                    });
        }, "t1");

        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(3_000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            IntStream.range(1, 1_000)
                    .forEach(i -> {
                        System.out.println(String.join(":",
                                Thread.currentThread().getName(), String.valueOf(i)));
                    });
        }, "t2");

        t1.start();
        t2.start();
        t2.join();
        System.out.println("t2.join()");
        t1.join();
        System.out.println("t1.join()");
        System.out.println("main completed");
    }

}
