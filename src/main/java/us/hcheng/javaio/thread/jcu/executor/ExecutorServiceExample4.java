package us.hcheng.javaio.thread.jcu.executor;

import us.hcheng.javaio.utils.SleepUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ExecutorServiceExample4 {

    public static void main(String[] args) {
        //invokeAnyTest();
        //invokeAnyTimeoutTest();
        //invokeAllTest();
        //invokeAllTimeoutTest();
        //submitRunnableTest();
        submitRunnableWithObjectTest();
    }

    public static void invokeAnyTest() {
        ExecutorService service = Executors.newFixedThreadPool(3);
        List<Callable<Integer>> callables = IntStream.range(0, 10).mapToObj(i -> {
            return new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    Thread t = Thread.currentThread();
                    System.out.println(i + " executing... by " + t);
                    if (i == 0){
                        if (new Random().nextBoolean())
                            throw new RuntimeException("haha 0");
                        else
                            return 0;
                    }

                    SleepUtil.sleepSecRandom();
                    System.err.println("returning res: " + (i * 2) + " by " + t);
                    return i * 2;
                }
            };
        }).collect(Collectors.toList());
        try {
            Integer res = service.invokeAny(callables);
            System.err.println("res: " + res);
        } catch (InterruptedException e) {
            System.err.println("InterruptedException");
        } catch (ExecutionException e) {
            System.err.println("ExecutionException");
        }

        service.shutdown();
    }

    public static void invokeAnyTimeoutTest() {
        ExecutorService service = Executors.newFixedThreadPool(3);
        List<Callable<Integer>> callables = IntStream.range(0, 10).mapToObj(i -> {
            return new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    Thread t = Thread.currentThread();
                    System.out.println(i + " executing... by " + t);
                    SleepUtil.sleepSecRandom();
                    System.err.println("returning res: " + (i * 2) + " by " + t);
                    return i * 2;
                }
            };
        }).collect(Collectors.toList());
        try {
            Integer res = service.invokeAny(callables, 1, TimeUnit.SECONDS);
            System.err.println("res: " + res);
        } catch (InterruptedException e) {
            System.err.println("InterruptedException");
        } catch (ExecutionException e) {
            System.err.println("ExecutionException");
        } catch (TimeoutException e) {
            System.err.println("TimeoutException");
        }

        service.shutdown();
    }

    public static void invokeAllTest() {
        ExecutorService service = Executors.newFixedThreadPool(3);
        List<Integer> list = new ArrayList<>();

        List<Callable<Integer>> callables = IntStream.range(0, 10).mapToObj(i -> {
            return new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    if (i % 2 == 0)
                        throw new RuntimeException();

                    Thread t = Thread.currentThread();
                    System.out.println(i + " executing... by " + t);
                    SleepUtil.sleepSecRandom();
                    System.err.println("returning res: " + (i * 2) + " by " + t);
                    return i * 2;
                }
            };
        }).collect(Collectors.toList());

        try {
            List<Future<Integer>> res = service.invokeAll(callables);
            res.forEach(f -> {
                try {
                    Integer r = f.get();
                    list.add(r);
                } catch (ExecutionException e) {
                    System.out.println("ExecutionException " + e);
                } catch (InterruptedException e) {
                    System.out.println("InterruptedException " + e);
                }
            });
        } catch (InterruptedException e) {
            System.err.println("InterruptedException");
        }

        System.out.println(list);
        System.err.println("Shutdown ExecutorService...");
        service.shutdown();
    }

    public static void invokeAllTimeoutTest() {
        ExecutorService service = Executors.newFixedThreadPool(3);
        List<Integer> list = new ArrayList<>();

        List<Callable<Integer>> callables = IntStream.range(0, 10).mapToObj(i -> {
            return new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    if (i % 2 == 0)
                        throw new RuntimeException();

                    Thread t = Thread.currentThread();
                    System.out.println(i + " executing... by " + t);
                    SleepUtil.sleepSecRandom();
                    System.err.println("returning res: " + (i * 2) + " by " + t);
                    return i * 2;
                }
            };
        }).collect(Collectors.toList());

        try {
            List<Future<Integer>> res = service.invokeAll(callables, 1, TimeUnit.SECONDS);
            res.forEach(f -> {
                try {
                    Integer r = f.get();
                    list.add(r);
                } catch (ExecutionException e) {
                    System.out.println("ExecutionException " + e);
                } catch (InterruptedException e) {
                    System.out.println("InterruptedException " + e);
                }
            });
        } catch (InterruptedException e) {
            System.err.println("InterruptedException");
        }

        System.out.println(list);
        System.err.println("Shutdown ExecutorService...");
        service.shutdown();
    }

    private static void submitRunnableTest() {
        ExecutorService service = Executors.newFixedThreadPool(3);

        Future<?> future = service.submit(new Runnable() {
            @Override
            public void run() {
                if (new Random().nextBoolean())
                    throw new RuntimeException("---");

                Thread t = Thread.currentThread();
                System.out.println(" executing... by " + t);
                SleepUtil.sleepSecRandom();
                System.err.println("returning by " + t);
            }
        });

        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            System.err.println("InterruptedException " + e);
        } catch (ExecutionException e) {
            System.err.println("ExecutionException " + e);  // when exceptions occurred in runnable
        }

        System.err.println("Shutdown ExecutorService...");
        service.shutdown();
    }

    private static void submitRunnableWithObjectTest() {
        ExecutorService service = Executors.newFixedThreadPool(3);

        Future<Date> future = service.submit(new Runnable() {
            @Override
            public void run() {
                if (new Random().nextBoolean())
                    throw new RuntimeException("---");
                Thread t = Thread.currentThread();
                System.out.println(" executing... by " + t);
                System.err.println("returning by " + t);
            }
        }, new Date());

        try {
            Date res = future.get();
            System.out.println(res);
        } catch (InterruptedException e) {
            System.err.println("InterruptedException " + e);
        } catch (ExecutionException e) {
            System.err.println("ExecutionException " + e);  // when exceptions occurred in runnable
        }

        System.err.println("Shutdown ExecutorService...");
        service.shutdown();
    }

}
