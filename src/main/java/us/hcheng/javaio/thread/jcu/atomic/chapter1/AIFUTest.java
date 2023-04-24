package us.hcheng.javaio.thread.jcu.atomic.chapter1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.stream.IntStream;

public class AIFUTest {

    private volatile int i;
    private AtomicInteger j = new AtomicInteger(0);

    private final AtomicIntegerFieldUpdater<AIFUTest> updater =
            AtomicIntegerFieldUpdater.newUpdater(AIFUTest.class, "i");

    public void incrementI() {
        if (!updater.compareAndSet(this, i, i + 1))
            incrementI();
    }

    public void incrementJ() {
        j.incrementAndGet();
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j.get();
    }


    public static void main(String[] args) throws InterruptedException {
        List<Thread> list = new ArrayList<>();
        AIFUTest aifuTest = new AIFUTest();

        IntStream.range(0, 10).forEach(i -> {
            list.add(new Thread(() -> {
                IntStream.range(0, 10).forEach(ii -> {
                    aifuTest.incrementI();
                    aifuTest.incrementJ();
                });
            }, i + "T"));
        });

        list.forEach(Thread::start);
        for (Thread t : list)
            t.join();

        System.out.println(aifuTest.getI() + " " + aifuTest.getJ());
    }
}
