package us.hcheng.javaio.thread.part2.chapter17;

import us.hcheng.javaio.thread.part2.chapter17.processor.WorkerThread;
import us.hcheng.javaio.utils.SleepUtil;

import java.util.Arrays;

public class Channel {
    private int head;
    private int tail;
    private int count;
    private final int total;
    private volatile boolean terminated = false;
    private final WorkerThread[] workers;
    private final Request[] requests;
    private final int MAX_REQUEST_SIZE = 10;

    public Channel(int size) {
        this.total = Math.max(size, 5);
        this.workers = new WorkerThread[this.total];
        this.requests = new Request[MAX_REQUEST_SIZE];
        this.init();
    }

    private void init() {
        for (int i = 0; i < total; i++) {
            this.workers[i] = new WorkerThread(String.join("-", "Line Worker", String.valueOf(i)), this);
            this.workers[i].start();
        }

        new Thread(() -> {
            for (int zeroCount = 0; !terminated; ) {
                SleepUtil.sleep(1000);
                System.out.println("Curr: " + count);
                if (this.count == 0) {
                    zeroCount++;
                    if (zeroCount == 3)
                        terminated = true;
                } else {
                    zeroCount = 0;
                }
            }

            System.err.println("Stopping...");
            Arrays.stream(this.workers).forEach(WorkerThread::interrupt);
        }, "Service Monitor").start();
    }

    public synchronized void put(Request req) {
        try {
            while (this.count >= MAX_REQUEST_SIZE)
                this.wait();

            this.requests[this.tail] = req;
            this.tail = (this.tail + 1) % MAX_REQUEST_SIZE;
            this.count++;
            this.notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized Request get() {
        Request ret = null;

        try {
            while (count == 0)
                this.wait();

            ret = this.requests[this.head];
            this.count--;
            this.head = (this.head + 1) % MAX_REQUEST_SIZE;
            this.notifyAll();
        } catch (InterruptedException e) {
            System.out.println("Channel closing...");
        }

        return ret;
    }


    public boolean isTerminated() {
        return terminated;
    }

}
