package us.hcheng.javaio.thread.part1.chapter9;

public class ProducerConsumerV2 {

    private int jobId;
    private boolean produced;
    private final int finalJob = 4;
    private final Object monitor = new Object();

    public void produce() throws InterruptedException {
        synchronized (monitor) {
            if (this.produced) {
                System.out.println("Produce wait: " + Thread.currentThread().getName());
                monitor.wait();
            } else {
                this.jobId++;
                System.out.println("Produce: " + this.jobId + " === " + Thread.currentThread().getName());
                this.produced = true;
                monitor.notify();
            }
        }
    }

    public void consume() throws InterruptedException {
        synchronized (monitor) {
            if (this.produced) {
                System.out.println("Consume: " + this.jobId + " === " + Thread.currentThread().getName());
                this.produced = false;
                monitor.notify();
            } else {
                System.out.println("Consumer wait: " + Thread.currentThread().getName());
                monitor.wait();
            }
        }
    }

    public boolean complete() {
        return this.jobId >= this.finalJob;
    }

    public static void main(String[] args) {
        ProducerConsumerV2 v2 = new ProducerConsumerV2();

        new Thread(()->{
            while (!v2.complete()) {
                try {
                    v2.consume();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "T-consumer").start();

        new Thread(()->{
            while (!v2.complete()) {
                try {
                    v2.produce();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "T-producer").start();
    }

}
