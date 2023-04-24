package us.hcheng.javaio.thread.jcu.atomic.chapter1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NonAtomicIntegerTest {

    private static int integer = 0;

    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        Set<Integer> intSet = new HashSet<>();

        Stream.of("T1", "T2", "t3").forEach(each -> {
            threads.add(new Thread(() -> {
                IntStream.range(0, 500).forEach(i -> {
                    intSet.add(integer++);
                });
            }, each));
        });

        threads.forEach(Thread::start);
        for (Thread t : threads) {
            t.join();
        }

        System.out.println(intSet.size());
    }

}
