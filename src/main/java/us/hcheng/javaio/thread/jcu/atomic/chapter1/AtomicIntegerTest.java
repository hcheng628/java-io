package us.hcheng.javaio.thread.jcu.atomic.chapter1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class AtomicIntegerTest {

    private static final AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        Set<Integer> atomicSet = new HashSet<>();

        Stream.of("T1", "T2", "t3").forEach(each -> {
            threads.add(new Thread(() -> {
                IntStream.range(0, 500).forEach(i -> {
                    atomicSet.add(atomicInteger.addAndGet(1));
                });
            }, each));
        });

        threads.forEach(Thread::start);
        for (Thread t : threads) {
            t.join();
        }

        System.out.println(atomicSet.size());
    }

}
