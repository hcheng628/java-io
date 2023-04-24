package us.hcheng.javaio.thread.part2.chapter10;


import us.hcheng.javaio.thread.part2.chapter10.entity.Person;

import java.util.Random;

public class ThreadLocalComplexTest {

    private static final ThreadLocal<Person> threadLocal = new ThreadLocal<>(){
        @Override
        protected Person initialValue() {
            return new Person("DEFAULT");
        }
    };

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread() + " " + threadLocal.get());
            threadLocal.set(new Person("T1"));
            try {
                Thread.sleep(new Random(System.currentTimeMillis()).nextInt(1000));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.out.println(Thread.currentThread() + " " + threadLocal.get());
        }, "T1");

        Thread t2 = new Thread(() -> {
            System.out.println(Thread.currentThread() + " " + threadLocal.get());
            threadLocal.set(new Person("T1"));
            try {
                Thread.sleep(new Random(System.currentTimeMillis()).nextInt(1000));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.out.println(Thread.currentThread() + " " + threadLocal.get());
        }, "T2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(threadLocal.get());
    }

}
