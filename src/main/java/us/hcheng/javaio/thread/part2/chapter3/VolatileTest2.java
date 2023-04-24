package us.hcheng.javaio.thread.part2.chapter3;

import java.util.*;
import java.util.stream.IntStream;

public class VolatileTest2 {
    private static int INIT_VALUE = 0;
    private static int MAX_LIMIT = 1000;

    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        List<Integer> list = new ArrayList<>();

        Arrays.asList("Adder1", "Adder2")
                .forEach(name -> {
                    threads.add(new Thread(() -> {
                        while (INIT_VALUE < MAX_LIMIT) {
                            list.add(INIT_VALUE);
                            INIT_VALUE++;
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }, name));
                });

        threads.forEach(Thread::start);
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println(list.size() + " vs " + new HashSet<>(list).size());
    }

}
