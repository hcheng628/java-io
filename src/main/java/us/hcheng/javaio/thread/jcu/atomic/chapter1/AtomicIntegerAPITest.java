package us.hcheng.javaio.thread.jcu.atomic.chapter1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class AtomicIntegerAPITest {


    public static void main(String[] args) {
        //duplidates();
        noDuplicates();
    }

    public static void noDuplicates() {
        AtomicInteger atomicInteger = new AtomicInteger();
        List<Thread> threads = new ArrayList<>();
        List<Integer> list = new CopyOnWriteArrayList<>();
        List<String> count = new CopyOnWriteArrayList<>();

        Stream.of("T1", "T2", "T3").forEach(each -> {
            Thread t = new Thread(() -> {
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        System.err.println(e);
                    }
                    int val = atomicInteger.getAndIncrement();
                    list.add(val);
                    count.add(Thread.currentThread() + " " + val);
                    System.out.println(Thread.currentThread() + " " + val);
                }
            }, each);
            threads.add(t);
        });

        threads.forEach(Thread::start);
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }

        System.out.println(list.size() + " vs " + new HashSet<>(list).size());
        System.out.println(list + " count: " + count.size());
        //System.out.println(count);
    }

    public static void duplidates() {
        AtomicInteger atomicInteger = new AtomicInteger(10);
        List<Integer> list = new ArrayList<>();

        Stream.of("T1", "T2", "T3").forEach(each -> {
            new Thread(() -> {
                IntStream.range(0, 10).forEach(i -> {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    atomicInteger.set(atomicInteger.get() + 1);
                    list.add(atomicInteger.get());
                });
            }, each).start();
        });

        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(list.size() + " vs " + new HashSet<>(list).size());
    }

}
