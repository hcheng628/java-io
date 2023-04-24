package us.hcheng.javaio.thread.part1.chapter7;

public class TicketWindowRunnable implements Runnable {

    private static final int MAX = 500;
    private static final Object MONITOR = new Object();
    private static int curr = 0;

    @Override
    public void run() {
        synchronized (MONITOR) {
            while (curr <= MAX) {
                try {
                    Thread.sleep(5);
                        System.out.println(String.join("-", Thread.currentThread().getName(), String.join(":", "TKT", String.valueOf(curr++))));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
