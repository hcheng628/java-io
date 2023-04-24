package us.hcheng.javaio.thread.part1.chapter10;

public class SynchronizedFact {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(SynchronizedFact::run,"T1");
        Thread t2 = new Thread(SynchronizedFact::run,"T1");

        t1.start();
        Thread.sleep(1_000);
        t2.start();

        t1.interrupt();
        t2.interrupt();
    }

    private synchronized static void run() {
        System.out.println(Thread.currentThread().getName());
        while (true) {
        }
    }
}
