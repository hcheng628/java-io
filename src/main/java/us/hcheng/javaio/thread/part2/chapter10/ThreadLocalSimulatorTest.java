package us.hcheng.javaio.thread.part2.chapter10;

import us.hcheng.javaio.thread.part2.chapter10.service.ThreadLocalSimulator;

import java.util.Random;

public class ThreadLocalSimulatorTest {

    private static final ThreadLocalSimulator<String> threadLocal = new ThreadLocalSimulator<>() {
        @Override
        public String initialValue() {
            return "No Value";
        }
    };

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            threadLocal.set("Thread-T1");
            try {
                Thread.sleep(RANDOM.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ":" + threadLocal.get());
        }, "Thread-T1");
        Thread t2 = new Thread(() -> {
            threadLocal.set("Thread-T2");
            try {
                Thread.sleep(RANDOM.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ":" + threadLocal.get());
        }, "Thread-T2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(Thread.currentThread().getName() + ":" + threadLocal.get());
    }

}
