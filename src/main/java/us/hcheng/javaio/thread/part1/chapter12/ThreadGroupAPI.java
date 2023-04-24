package us.hcheng.javaio.thread.part1.chapter12;

import java.util.Arrays;

public class ThreadGroupAPI {

    public static void main(String[] args) {
        ThreadGroup threadGroup1 = new ThreadGroup("ThreadGroup1");
        Thread t1 = new Thread(threadGroup1, () -> {
            while (true) {
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }, "t1");
        t1.start();

        ThreadGroup threadGroup2 = new ThreadGroup(threadGroup1, "ThreadGroup2");
        Thread t2 = new Thread(threadGroup2, () -> {
            while (true) {
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }, "t2");
        t2.start();

        System.out.println("threadGroup1 active count is " + threadGroup1.activeCount());
        System.out.println(threadGroup1.activeGroupCount());

        System.out.println("===========");
        Thread[] threads = new Thread[threadGroup1.activeCount()];
        threadGroup1.enumerate(threads);
        System.out.println(Arrays.toString(threads));

        System.out.println("===========");
        threads = new Thread[threadGroup1.activeCount()];
        threadGroup1.enumerate(threads, false);
        System.out.println(Arrays.toString(threads));

        ThreadGroup mainThreadGroup = Thread.currentThread().getThreadGroup();
        threads = new Thread[mainThreadGroup.activeCount()];
        mainThreadGroup.enumerate(threads);
        System.out.println(Arrays.toString(threads));
        System.out.println("---");
        Thread[] nonRecThreads = new Thread[mainThreadGroup.activeCount()];
        mainThreadGroup.enumerate(nonRecThreads,false);
        System.out.println(Arrays.toString(nonRecThreads));

        mainThreadGroup.list();
    }

}
