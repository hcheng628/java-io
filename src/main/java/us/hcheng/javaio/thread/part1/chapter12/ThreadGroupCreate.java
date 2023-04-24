package us.hcheng.javaio.thread.part1.chapter12;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ThreadGroupCreate {

    public static void main(String[] args) {
        //printThreadGroupInfo();

        ThreadGroup threadGroup1 = new ThreadGroup("ThreadGroup1");
        Thread t1 = new Thread(threadGroup1, () -> {
            while (true) {
                ThreadGroup currThreadGroup = Thread.currentThread().getThreadGroup();

                Thread[] threads = new Thread[currThreadGroup.getParent().activeCount()];
                currThreadGroup.getParent().enumerate(threads);
                for (Thread t : threads)
                    System.out.println(t);

                StringBuilder sb = new StringBuilder();
                sb.append(String.join(": ", "getName", currThreadGroup.getName()));
                sb.append("\n");
                sb.append(String.join(": ", "Parent getName", currThreadGroup.getParent().getName()));
                sb.append("\n");
                sb.append(String.join(": ", "Parent activeCount", String.valueOf(currThreadGroup.getParent().activeCount())));
                sb.append("\n");

                System.out.println(sb);
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (1 == 1)
                    break;
            }
        }, "t1");

        t1.start();
        System.out.println("t1's thread group name = " + t1.getThreadGroup().getName());// TG1

        ThreadGroup threadGroup2 = new ThreadGroup(threadGroup1, "ThreadGroup2");
        printThreadGroupInfo(threadGroup2);
        new Thread(threadGroup2, () -> {
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "t2").start();

        ThreadGroup threadGroup3 = new ThreadGroup("ThreadGroup3");
        new Thread(threadGroup3, () -> {
            System.out.println(t1.getName());
            Thread[] threads = new Thread[threadGroup1.activeCount()];
            threadGroup1.enumerate(threads,false);
            Stream.of(threads).forEach(System.out::println);
        }, "t3").start();
    }

    private static void printThreadGroupInfo(ThreadGroup threadGroup) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.join(": ", "ThreadGroup Name", threadGroup.getName()));
        sb.append("\n");
        sb.append(String.join(": ", "Parent ThreadGroup Name", threadGroup.getParent().getName()));
        System.out.println(sb);
    }

}
