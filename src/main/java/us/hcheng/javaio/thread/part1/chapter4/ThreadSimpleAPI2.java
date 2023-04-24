package us.hcheng.javaio.thread.part1.chapter4;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ThreadSimpleAPI2 {

    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>();
        IntStream.rangeClosed(1, 5).forEach(each -> {
            Thread t = new Thread(()-> {
                IntStream.range(1, 1000).forEach(i -> {
                    System.out.println(String.join(" - ",
                            Thread.currentThread().getName(), String.valueOf(i)));
                });
            }, String.join(":", "T", String.valueOf(each)));

            if (each % 2 == 0) {
                t.setPriority(Thread.MAX_PRIORITY);
            } else if (each == 5) {
                t.setPriority(Thread.MIN_PRIORITY);
            } else {
                t.setPriority(Thread.NORM_PRIORITY);
            }

            threads.add(t);
        });

        threads.forEach(t -> {
            t.start();
        });
    }

}
