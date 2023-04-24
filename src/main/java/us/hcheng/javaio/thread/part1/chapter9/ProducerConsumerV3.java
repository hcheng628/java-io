package us.hcheng.javaio.thread.part1.chapter9;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ProducerConsumerV3 {

    private int jobId;
    private boolean produced;
    private final int finalJob = 300;
    private final Object monitor = new Object();

    public void produce() throws InterruptedException {
        synchronized (monitor) {
            while(this.produced && !complete())
                monitor.wait();

            if (!complete()) {
                System.out.println("Produce: " + this.jobId++ + " === " + Thread.currentThread().getName());
                this.produced = true;
                monitor.notifyAll();
            }
        }
    }

    public void consume() throws InterruptedException {
        synchronized (monitor) {
            while (!this.produced)
                monitor.wait();

            if (!complete()) {
                //System.out.println("Consume: " + this.jobId + " === " + Thread.currentThread().getName());
                this.produced = false;
                monitor.notifyAll();
            }
        }
    }

    public boolean complete() {
        return this.jobId >= this.finalJob;
    }

    public static void main(String[] args) {
        ProducerConsumerV3 v3 = new ProducerConsumerV3();
        IntStream.range(1, 6).forEach(i -> {
            Stream.of("Consumer", "Producer").forEach(t -> {
                new Thread(() -> {
                    while (!v3.complete())
                        try {
                            Thread.sleep(6);
                            if (t.equals("Consumer"))
                                v3.consume();
                            else
                                v3.produce();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                }, String.join(":", t, String.valueOf(i))).start();
            });
        });
    }

}
