package us.hcheng.javaio.thread.jcu.executor.future;

import us.hcheng.javaio.thread.part2.chapter17.util.SleepUtil;

import java.util.concurrent.*;

public class FutureExample1 {

    /**
     * both InterruptedException and TimeoutException are
     * applied to the caller thread of the future.get()
     * @param args
     */
    public static void main(String[] args) {
        //getTest();
        //getWithTimeoutTest();
        interruptedTest();
    }

    private static void getTest() {
        ExecutorService service = Executors.newFixedThreadPool(3);

        Future<Integer> future = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                SleepUtil.sleepSecRandom();
                System.out.println("Processing Completed!");
                return 13;
            }
        });

        Thread main = Thread.currentThread();
        new Thread(() -> {
            SleepUtil.sleep(500);
            main.interrupt();
        }).start();

        Integer res = null;
        try {
            res = future.get();
        } catch (InterruptedException e) {
            System.out.println("InterruptedException " + e );
        } catch (ExecutionException e) {
            System.out.println("ExecutionException " + e);
        } finally {
            System.out.println("res: " + res);
        }
    }

    private static void getWithTimeoutTest() {
        ExecutorService service = Executors.newFixedThreadPool(3);

        Future<Integer> future = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                SleepUtil.sleepSecRandom();
                System.out.println("Processing Completed!");
                return 13;
            }
        });

        Integer res = null;
        try {
            res = future.get(300, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            System.out.println("InterruptedException " + e );
        } catch (ExecutionException e) {
            System.out.println("ExecutionException " + e);
        } catch (TimeoutException e) {
            System.out.println("TimeoutException " + e);
        } finally {
            System.out.println("res: " + res + " isInterrupted: " + Thread.currentThread().isInterrupted());
        }
    }

    private static void interruptedTest() {
        ThreadPoolExecutor service = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);

        service.execute(() -> {
            Thread t = Thread.currentThread();
            System.out.println(t + " 1 processing start..." + t.isInterrupted());
            t.interrupt();
            //SleepUtil.sleep(1000 * 20);
            System.out.println(t + " 1 processing complete..." + t.isInterrupted());
        });

        System.out.println(service.getCompletedTaskCount());
        SleepUtil.sleep(1000);
        System.out.println(service.getCompletedTaskCount());

        SleepUtil.sleep(3000);
        service.execute(() -> {
            Thread t = Thread.currentThread();
            System.out.println(t + " 2 processing start..." + t.isInterrupted());
            t.interrupt();
            //SleepUtil.sleep(1000 * 20);
            System.out.println(t + " 2 processing complete..." + t.isInterrupted());
        });
        SleepUtil.sleep(1000);
        System.out.println(service.getCompletedTaskCount());
    }
}
