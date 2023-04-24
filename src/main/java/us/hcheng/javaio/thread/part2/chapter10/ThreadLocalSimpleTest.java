package us.hcheng.javaio.thread.part2.chapter10;

import us.hcheng.javaio.thread.part2.chapter10.entity.Person;

public class ThreadLocalSimpleTest {

    private static ThreadLocal<Person> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        System.out.println(threadLocal.get());
        threadLocal.set(new Person("Moka"));
        Thread.sleep(1_000);
        System.out.println(threadLocal.get());
    }

}
