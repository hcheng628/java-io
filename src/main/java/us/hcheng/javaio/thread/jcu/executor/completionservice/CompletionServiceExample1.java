package us.hcheng.javaio.thread.jcu.executor.completionservice;

import us.hcheng.javaio.thread.part2.chapter17.util.SleepUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CompletionServiceExample1 {

    public static void main(String[] args) {
        //futureFact1();
        futureFact2();
    }

    /**
     * get() is blocking and no callback
     */
    private static void futureFact1() {
        ExecutorService service = Executors.newFixedThreadPool(2);
        Future<Integer> future = service.submit(() -> {
            SleepUtil.sleepSec(100);
            System.out.println("processing");
            return 100;
        });

        System.out.println("**********");
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * fast jobs has to wait for slow jobs
     */
    private static void futureFact2() {
        ExecutorService service = Executors.newFixedThreadPool(2);
        List<Future<Integer>> futures = new ArrayList<>();

        futures.add(service.submit(() -> {
            int time = 10;
            SleepUtil.sleepSec(time);
            System.out.println("processing " + time);
            return time;
        }));

        futures.add(service.submit(() -> {
            int time = 5;
            SleepUtil.sleepSec(time);
            System.out.println("processing " + time);
            return time;
        }));

        System.out.println("ALL JOBS ARE SUBMITTED!");

        try {
            System.out.println(futures.get(0).get());
            System.out.println(futures.get(1).get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

    }

}
