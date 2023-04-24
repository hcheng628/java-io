package us.hcheng.javaio.thread.part2.chapter14;

public class CustomCountDownLatch {

    private int curr;
    private final int  total;

    public CustomCountDownLatch(int total) {
        this.total = total;
    }

    public synchronized void countDown() {
        curr++;
        this.notifyAll();
    }

    public synchronized void await() throws InterruptedException {
        while (curr < total)
            this.wait();
    }
}
