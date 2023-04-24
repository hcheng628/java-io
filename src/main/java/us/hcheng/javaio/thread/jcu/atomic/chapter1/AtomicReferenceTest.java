package us.hcheng.javaio.thread.jcu.atomic.chapter1;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceTest {

    public static void main(String[] args) {
        AtomicReference<Person> reference = new AtomicReference<>();
        Person cheng = new Person("Cheng", 34);
        Person moka = new Person("Moka", 4);
        Person moka2 = new Person("Moka", 4);

        reference.set(moka);
        System.out.println(reference.get());

        boolean res = reference.compareAndSet(moka2, moka2);
        System.out.println(res + " | " + reference.get());

        res = reference.compareAndSet(moka, cheng);
        System.out.println(res + " | " + reference.get());

        AtomicReference<Integer> intRef = new AtomicReference<>();
        Integer a = 1;
        int aa = 1;

        intRef.set(a);
        System.out.println(intRef.get());
        res = intRef.compareAndSet(aa, 2);
        System.out.println(res + " | " + intRef.get());
    }

}
