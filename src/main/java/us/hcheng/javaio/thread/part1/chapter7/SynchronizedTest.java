package us.hcheng.javaio.thread.part1.chapter7;

public class SynchronizedTest {

    private static final Object MONITOR = new Object();


    public static void main(String[] args) {


        Runnable task = new Runnable() {
            @Override
            public void run() {
                synchronized (MONITOR) {
                    try {
                        Thread.sleep(300_000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };

        new Thread(task, "t11111").start();
        new Thread(task, "t22222").start();
        new Thread(task, "t33333").start();
    }


}
