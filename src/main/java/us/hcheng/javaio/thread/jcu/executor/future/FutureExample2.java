package us.hcheng.javaio.thread.jcu.executor.future;

import us.hcheng.javaio.thread.part2.chapter17.util.SleepUtil;

import java.util.concurrent.*;

public class FutureExample2 {

    public static void main(String[] args) {
        // isDoneTest();
        // cancelTest();
        cancelInQueueTest();
    }

    /**
     * isDone when 1. Completed, 2. Exceptions Occurred 3. Canceled
     */
    private static void isDoneTest() {
        ThreadPoolExecutor service = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);

        Future<Integer> future = service.submit(() -> {
            System.out.println("haha");
            return 100;
        });

        System.out.println("Before isDone: " + future.isDone());
        SleepUtil.sleepSecRandom();
        System.out.println("After isDone: " + future.isDone() + " tasks: " +service.getCompletedTaskCount());

        future = service.submit(() -> {
            throw new RuntimeException("haha");
        });

        System.out.println("Before isDone: " + future.isDone());
        SleepUtil.sleepSecRandom();
        System.out.println("After isDone: " + future.isDone() + " tasks: " +service.getCompletedTaskCount());


        service.submit(() -> {
            SleepUtil.sleep(1000 * 100);
            return 100;
        });

        future = service.submit(() -> {
            while (true) {
                System.out.println("...");
            }
        });
        System.out.println("cancel: " + future.cancel(true));
        System.out.println("Before isDone: " + future.isDone());
        SleepUtil.sleepSecRandom();
        System.out.println("After isDone: " + future.isDone() + " tasks: " +service.getCompletedTaskCount());
    }

    private static void cancelTest() {
        ThreadPoolExecutor service = (ThreadPoolExecutor) Executors.newFixedThreadPool(1, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread ret = new Thread(r);
                ret.setDaemon(true); // when thread is interrupted, it will make that thread to quit
                return ret;
            }
        });
        Future<Integer> future = service.submit(() -> {
            for (Boolean flag = null; flag == null || !flag || 1 == 1;) {
                if (flag == null || Thread.currentThread().isInterrupted() != flag) {
                    flag = Thread.currentThread().isInterrupted();
                    System.err.println(flag + " at " + System.currentTimeMillis());
                } else {
                    Thread t = Thread.currentThread();
                    System.out.println("moka: " + t.isInterrupted());
                }
            }

            // if setDaemon(true) this below might not be executed
            System.out.println("final...");
            return 100;
        });

        SleepUtil.sleep(500);
        boolean cancelRes = future.cancel(true);
        System.out.println("cancel: " + cancelRes + " at " + System.currentTimeMillis());

        if (cancelRes) {
            try {
                // CancellationException
                future.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (CancellationException e) {
                System.err.println("moka catch");
            } finally {
                System.err.println("moka finally");
            }
        }
    }

    private static void cancelInQueueTest() {
        ThreadPoolExecutor service = new ThreadPoolExecutor(0, 1,
                10L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1));

        service.submit(() -> {
            SleepUtil.sleepSec(10);
            System.out.println("1st job completed...");
        });

        SleepUtil.sleepSec(1);
        Future<Integer> future = service.submit(() -> {
            System.out.println("I should be canceled");
            return 100;
        });

        try {
            service.submit(() -> {
                System.out.println("exception!!!");
                return 100;
            });
        } catch (Exception ex) {
            System.out.println("I dont care!");
        }

        SleepUtil.sleepSec(1);
        System.out.println("canceled? " + future.cancel(true));
        System.out.println("job count? " + service.getCompletedTaskCount());

        SleepUtil.sleepSec(10);
        System.out.println("job count? " + service.getCompletedTaskCount());

    }
}
