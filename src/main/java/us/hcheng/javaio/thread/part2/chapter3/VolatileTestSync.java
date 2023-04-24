package us.hcheng.javaio.thread.part2.chapter3;

public class VolatileTestSync {

    private static int INIT_VALUE = 0;
    private static final  int MAX_VALUE = 5;
    private static final Object LOCK = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            int local = INIT_VALUE;
                while (local < MAX_VALUE) {
                    synchronized (LOCK) {
                        if (local != INIT_VALUE) {
                            System.out.println(Thread.currentThread().getName() + " Update " + local + " vs " + INIT_VALUE);
                            local = INIT_VALUE;
                        }
                    }
                }
        }, "READER").start();

        new Thread(() -> {
            for (int local = INIT_VALUE; local <= MAX_VALUE; local++) {
                try {
                    Thread.sleep(300);
                    synchronized (LOCK) {
                        INIT_VALUE = local;
                    }
                    System.out.println(Thread.currentThread().getName() + " Update " + INIT_VALUE);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "UPDATER").start();

    }

}
