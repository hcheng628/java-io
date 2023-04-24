package us.hcheng.javaio.thread.part1.chapter6;

public class ThreadCloseGraceful {

    private static class Worker extends Thread {
        private volatile boolean start = true;

        @Override
        public void run() {
            while (start) {
                try {
                    Thread.sleep(500);
                    System.out.println("Working...");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public void shutdown() {
            this.start = false;
        }

    }

    public static void main(String[] args) throws InterruptedException {
        Worker worker = new Worker();
        worker.start();
        Thread.sleep(3_000);
        worker.shutdown();
    }

}
