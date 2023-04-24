package us.hcheng.javaio.thread.part1.chapter4;

public class DaemonThread2 {

    public static void main(String[] args) {
        Thread outerThread = new Thread(() -> {
            Thread innerThread = new Thread(()-> {
               try {
                   while (true) {
                       System.out.println("innerThread ... " + Thread.currentThread().getThreadGroup().activeCount());
                       Thread.sleep(3_000);
                   }
               } catch (InterruptedException ex) {
                   ex.printStackTrace();
               }
            });
            innerThread.setDaemon(true);
            innerThread.start();

            try {
                Thread.sleep(1_000);
                System.out.println("outerThread finish done. ...");
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        outerThread.setDaemon(true);
        outerThread.start();

    }
}
