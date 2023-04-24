package us.hcheng.javaio.thread.part1.chapter3;

import java.util.stream.Stream;

public class createThread2 {

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(100_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t.start();

        System.out.println("getThreadGroup: " + t.getThreadGroup());
        Thread currThread = Thread.currentThread();
        System.out.println("currThread.getName(): " + currThread.getName());
        System.out.println("getThreadGroup: " + currThread.getThreadGroup().getName());
        System.out.println("-----------");

        ThreadGroup threadGroup = currThread.getThreadGroup();
        System.out.println("threadGroup activeCount: " + threadGroup.activeCount());


        Thread[] tInfo = new Thread[threadGroup.activeCount()];
        threadGroup.enumerate(tInfo);
        Stream.of(tInfo).forEach(System.out::println);
    }

}
