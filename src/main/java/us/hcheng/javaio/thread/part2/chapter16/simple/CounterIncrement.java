package us.hcheng.javaio.thread.part2.chapter16.simple;

public class CounterIncrement extends Thread {

    private int counter;
    private boolean terminated;


    @Override
    public void run() {
        try {
            while (!this.terminated)
                System.out.println("Processing... " + counter++);
        } finally {
            beforeShutdown();
        }
    }

    public void shutdown() {
        this.terminated = true;
        this.interrupt();
    }

    private void beforeShutdown() {
        System.out.println("Close and free up resources");
    }

}
