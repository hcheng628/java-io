package us.hcheng.javaio.thread.part2.chapter3;

public class VolatileTest {

    /*
    uncomment and comment the below 2 lines to see the difference
    volatile ensures to read the latest data not cached data from RAM
     */
    // private static int INIT_VALUE = 0;
    private static volatile int INIT_VALUE = 0;

    private static final int MAX_VALUE = 5;

    public static void main(String[] args) {
        new Thread(() -> {
            int local = INIT_VALUE;
            while (local < MAX_VALUE) {
                if (local != INIT_VALUE) {
                    System.out.println(Thread.currentThread().getName() + " Update " + local
                    + " vs " + INIT_VALUE);
                    local = INIT_VALUE;
                }
            }
        }, "READER").start();

        new Thread(() -> {
            for (int local = INIT_VALUE; local <= MAX_VALUE; local++) {
                try {
                    Thread.sleep(300);
                    INIT_VALUE = local;
                    System.out.println(Thread.currentThread().getName() + " Update " + INIT_VALUE);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "UPDATER").start();

    }

}
