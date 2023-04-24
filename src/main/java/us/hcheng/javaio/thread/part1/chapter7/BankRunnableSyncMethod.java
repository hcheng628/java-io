package us.hcheng.javaio.thread.part1.chapter7;

public class BankRunnableSyncMethod {

    public static void main(String[] args) {
        SynchronizedRunnable runnable = new SynchronizedRunnable();
        new Thread(runnable, "一号窗口").start();
        new Thread(runnable, "二号窗口").start();
        new Thread(runnable, "三号窗口").start();
    }

}
