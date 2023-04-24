package us.hcheng.javaio.thread.part1.chapter7;

public class SynchronizedThis {

    public static void main(String[] args) {
        ThisLock lock1 = new ThisLock();
        ThisLock lock2 = new ThisLock();

        new Thread(()->{
            lock1.method1();
            lock2.method2();
        }, "t1").start();

        new Thread(()->{
            lock2.method1();
            lock1.method2();
        }, "t2").start();
    }

}

class ThisLock {

    public synchronized void method1() {
        try {
            System.out.println(Thread.currentThread().getName() + " executing method1");
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void method2() {
        try {
            System.out.println(Thread.currentThread().getName() + " executing method2");
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
