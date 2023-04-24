package us.hcheng.javaio.thread.jcu.atomic.chapter1;

import us.hcheng.javaio.thread.jcu.atomic.chapter1.entity.TestMe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.stream.IntStream;

public class AtomicIntegerFieldUpdaterTest {
    static final AtomicIntegerFieldUpdater<TestMe> fieldUpdater = AtomicIntegerFieldUpdater.newUpdater(TestMe.class, "pubVolatile");


    public static void main(String[] args) {
        TestMe testMe = new TestMe();
        List<Thread> threads = new ArrayList<>();

        IntStream.range(0, 2).forEach(i -> {
            threads.add(new Thread(() -> {
                IntStream.range(0, 10).forEach(each -> {
                    int val = fieldUpdater.getAndIncrement(testMe);
                    System.out.println(Thread.currentThread() + " = " + val);
                });
            }, i + "T"));
        });

        threads.forEach(Thread::start);
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println(testMe);
    }

}
