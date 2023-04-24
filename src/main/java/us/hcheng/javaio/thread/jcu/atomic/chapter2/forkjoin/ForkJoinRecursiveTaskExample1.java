package us.hcheng.javaio.thread.jcu.atomic.chapter2.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class ForkJoinRecursiveTaskExample1 extends RecursiveTask<Long> {

    private final int BATCH_SIZE = 100;
    private int start;
    private int end;

    public ForkJoinRecursiveTaskExample1(int start, int end) {
        this.start = start;
        this.end = end;
    }


    @Override
    protected Long compute() {
        if (start - end + 1 <= BATCH_SIZE) {
            return (long) IntStream.rangeClosed(start, end).sum();
        } else {
            int mid = (end - start) / 2 + start;
            ForkJoinRecursiveTaskExample1 left = new ForkJoinRecursiveTaskExample1(start, mid);
            ForkJoinRecursiveTaskExample1 right = new ForkJoinRecursiveTaskExample1(mid + 1, end);
            return  left.join() + right.join();
        }
    }

    public static void main(String[] args) {
        calculate(0);
        calculate(10);
        calculate(100);
        calculate(1000);
        calculate(10000);
        calculate(100000);
        calculate(1000000);
        calculate(10000000);
    }

    private static void calculate(int num) {
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> future = pool.submit(new ForkJoinRecursiveTaskExample1(0, num));
        try {
            Long res = future.get();
            System.out.println("[0-" + num+"]: " + res + " vs " + IntStream.rangeClosed(0, num).sum());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
