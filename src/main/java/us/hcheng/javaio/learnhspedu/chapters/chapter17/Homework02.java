package us.hcheng.javaio.learnhspedu.chapters.chapter17;

import us.hcheng.javaio.utils.SleepUtil;

public class Homework02 {

    private static int MONEY = 10000;
    private static final Object LOCK = new Object();

    public static void main(String[] args) {
        new Thread(Homework02::withdraws, "T1").start();
        new Thread(Homework02::withdraws, "T2").start();
    }

    private static void withdraws() {
        while(MONEY != 0) {
            withdraw();
            SleepUtil.sleep(50);
        }
    }

    private static void withdraw() {
        synchronized (LOCK) {
            if (MONEY >= 1000) {
                MONEY -= 1000;
                System.out.println(MONEY + " after "+ Thread.currentThread().getName() + " withdraw");
            }
        }
    }

}
