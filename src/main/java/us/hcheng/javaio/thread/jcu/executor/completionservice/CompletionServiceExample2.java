package us.hcheng.javaio.thread.jcu.executor.completionservice;

import us.hcheng.javaio.thread.part2.chapter17.util.SleepUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class CompletionServiceExample2 {

    public static void main(String[] args) {
        //takeTest();
        //pollTest();
        submitRunnableTest();
    }

    private static void takeTest() {
        ExecutorService service = Executors.newFixedThreadPool(2);
        List<Future<Integer>> futures = new ArrayList<>();
        CompletionService completionService = new ExecutorCompletionService(service);

        Stream.of(3,5,7,9,11,13).forEach(i -> {
            futures.add(completionService.submit(new Callable() {
                @Override
                public Object call() throws Exception {
                    System.out.println("processing " + i);
                    SleepUtil.sleepSec(i);
                    return i;
                }
            }));
        });

        try {
            for (Future<Integer> res = null; (res = completionService.take()) != null; )
                System.err.println("res:" + res.get());
        } catch (InterruptedException e) {
            System.err.println("InterruptedException " + e);
        } catch (ExecutionException e) {
            System.err.println("ExecutionException " + e);
        }
    }

    private static void pollTest() {
        ExecutorService service = Executors.newFixedThreadPool(2);
        List<Future<Integer>> futures = new ArrayList<>();
        CompletionService completionService = new ExecutorCompletionService(service);

        Stream.of(3,5,7,9,11,13).forEach(i -> {
            futures.add(completionService.submit(new Callable() {
                @Override
                public Object call() throws Exception {
                    System.out.println("processing " + i);
                    SleepUtil.sleepSec(i);
                    return i;
                }
            }));
        });

        while (true) {
            try {
                //SleepUtil.sleepSec(1);
                Future<Integer> future = completionService.poll(7, TimeUnit.SECONDS);
                System.err.println(future == null ? "not ready" : future.get());
            } catch (InterruptedException e) {
                System.out.println("InterruptedException " + e);
            } catch (ExecutionException e) {
                System.out.println("ExecutionException " + e);
            }
        }
    }

    private static void submitRunnableTest() {
        ExecutorService service = Executors.newFixedThreadPool(2);
        CompletionService<Result> completionService = new ExecutorCompletionService<>(service);
        Result result = new Result();
        completionService.submit(new ResultRunnable(result), result);

        try {
            System.err.println("final answer: " + completionService.take().get().getValue());
        } catch (InterruptedException e) {
            System.err.println("InterruptedException: " + e);
        } catch (ExecutionException e) {
            System.err.println("ExecutionException : " + e);
        }
    }

    static class Result {
        Integer value;

        public Integer getValue() {
            return this.value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    static class ResultRunnable implements Runnable {

        private final Result result;

        public ResultRunnable(Result result) {
            this.result = result;
        }

        @Override
        public void run() {
            SleepUtil.processing();
            this.result.setValue(new Random().nextInt(100));
        }
    }

}
