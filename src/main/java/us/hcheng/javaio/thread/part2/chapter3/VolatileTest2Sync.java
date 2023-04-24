package us.hcheng.javaio.thread.part2.chapter3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class VolatileTest2Sync {

    /*
        looks like not necessary to use volatile but
        use it to ensure while evaluation always has the latest INIT_VALUE
     */

    private static volatile int INIT_VALUE = 0;
    private static int MAX_LIMIT = 10000;

    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        List<Integer> list = new ArrayList<>();

        Arrays.asList("Adder1", "Adder2", "Adder3", "Adder4", "Adder5")
                .forEach(name -> {
                    threads.add(new Thread(() -> {
                        while (INIT_VALUE < MAX_LIMIT) {
                            synchronized (VolatileTest2Sync.class) {
                                list.add(INIT_VALUE++);
                            }

                            try {
                                Thread.sleep(3);
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
