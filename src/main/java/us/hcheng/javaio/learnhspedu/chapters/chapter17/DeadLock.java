package us.hcheng.javaio.learnhspedu.chapters.chapter17;

import us.hcheng.javaio.utils.SleepUtil;

public class DeadLock {

    public static void main(String[] args) {
        MyRunnable r1 = new MyRunnable(true);
        MyRunnable r2 = new MyRunnable(false);
        new Thread(r1).start();
        new Thread(r2).start();
    }

}

class MyRunnable implements Runnable {
    private static final Object LOCK1 = new Object();
    private static final Object LOCK2 = new Object();
    private boolean flag;

    public MyRunnable(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();

        if (flag) {
            synchronized (LOCK1) {
                System.out.println(name + " got Lock 1 now I need Lock 2 to do work");
                synchronized (LOCK2) {
                    System.out.println(name + " got both Lock 1 and 2! Working...");
                }
            }
        } else {
            synchronized (LOCK2) {
                System.out.println(name + " got Lock 2 now I need Lock 1 to do work");
                synchronized (LOCK1) {
                    System.out.println(name + " got both Lock 1 and 2! Working...");
                }
            }
        }
    }

}
