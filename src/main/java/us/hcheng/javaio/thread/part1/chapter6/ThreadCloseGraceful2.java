package us.hcheng.javaio.thread.part1.chapter6;

public class ThreadCloseGraceful2 {

    private static class Worker extends Thread {

        @Override
        public void run() {
            while (true) {
                System.out.println("Working...");
                System.out.println("moka: " + Thread.interrupted());
                if (Thread.interrupted()) {
                    System.out.println("Stopping...");
                    break;
                }
            }
            System.out.println("Stopped!");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Worker worker = new Worker();
        worker.start();
        Thread.sleep(2_000);
        worker.interrupt();
    }
}
