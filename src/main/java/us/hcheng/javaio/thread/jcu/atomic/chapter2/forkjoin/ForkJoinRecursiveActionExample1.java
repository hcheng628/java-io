package us.hcheng.javaio.thread.jcu.atomic.chapter2.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class ForkJoinRecursiveActionExample1 extends RecursiveAction {

    private final int BATCH_SIZE = 100;
    private int start;
    private int end;
    private AtomicLong res = new AtomicLong();

    public ForkJoinRecursiveActionExample1(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if (start - end + 1 <= BATCH_SIZE) {
            res.addAndGet(IntStream.rangeClosed((int)start, (int)end).sum());
        } else {
            int mid = (end - start) / 2 + start;
            new ForkJoinRecursiveActionExample1(start, mid).fork();
            new ForkJoinRecursiveActionExample1(mid + 1, end).fork();
        }
    }


    public static void main(String[] args) {
        calculate(0);
        calculate(10);
        calculate(100);
        calculate(1000);
        calculate(10000);
        calculate(100000);
    }

    private static void calculate(int num) {
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinRecursiveActionExample1 action = new ForkJoinRecursiveActionExample1(0, num);
        pool.submit(action);
        try {
            pool.awaitTermination(1, TimeUnit.SECONDS);
            System.out.println(action.res.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
