package us.hcheng.javaio.thread.part1.chapter5;

public class ThreadJoin2 {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
           try {
               System.out.println("thread is running...");
               Thread.sleep(1_000);
               System.out.println("thread running complete");
           } catch (InterruptedException ex) {
               ex.printStackTrace();
           }
        });

        t1.start();
        t1.join(100, 10);

        Thread.currentThread().join();
        System.err.println("main completed");
    }

}
