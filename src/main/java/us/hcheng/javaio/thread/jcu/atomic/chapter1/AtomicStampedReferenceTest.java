package us.hcheng.javaio.thread.jcu.atomic.chapter1;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicStampedReferenceTest {

    public static void main(String[] args) throws InterruptedException {
        Person moka = new Person("Moka", 4);
        Person moka2 = new Person("Moka", 4);
        Person cheng = new Person("Cheng", 33);
        //test1(moka, cheng);
        test2(moka, moka2);
    }

    private static void test2(Person from, Person to) {
        int initStamp = 1;
        AtomicStampedReference<Person> reference = new AtomicStampedReference<>(from, initStamp);
        boolean res = reference.compareAndSet(to, from, initStamp, initStamp + 1);
        printRef(res, reference);

        res = reference.compareAndSet(from, to, initStamp, 100);
        printRef(res, reference);

        res = reference.compareAndSet(to, new Person("ABC", 0), 100, 1);
        printRef(res, reference);
    }

    private static void test1(Person from, Person to) {
        int initStamp = 1;
        AtomicStampedReference<Person> reference = new AtomicStampedReference<>(from, initStamp);

        Thread t1 = new Thread(()->{
            printRef(true, reference);
            boolean res = reference.compareAndSet(from, to, initStamp, initStamp + 1);
            printRef(res, reference);
            res = reference.compareAndSet(to, from, initStamp + 1, initStamp + 2);
            printRef(res, reference);
        }, "T1");

        Thread t2 = new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println(Thread.currentThread() + "|" + initStamp + " and next: " + (initStamp + 1));
            boolean res = reference.compareAndSet(from, to, initStamp, initStamp + 1);
            printRef(res, reference);
        }, "T2");

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void printRef(boolean success, AtomicStampedReference reference) {
        StringBuilder sb = new StringBuilder();
        sb.append(success).append('|')
                .append(reference.getReference()).append('|')
                .append(reference.getStamp()).append('|')
                .append(Thread.currentThread());
        System.out.println(sb);
    }

}
