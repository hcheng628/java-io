package us.hcheng.javaio.thread.jcu.executor;

import us.hcheng.javaio.thread.part2.chapter17.util.SleepUtil;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class ScheduledExecutorServiceExample2 {

    private static final AtomicLong timestamp = new AtomicLong();

    public static void main(String[] args) {
        //scheduleAtFixedRateTest();
        //scheduleWithFixedDelayTest();
        //continueExistingPeriodicTasksAfterShutdownPolicyTest();
        executeExistingDelayedTasksAfterShutdownPolicyTest();
    }

    /**
     * repeat a runnable task and this method is
     * NOT an interval correct method
     */
    private static void scheduleAtFixedRateTest() {
        ScheduledExecutorService service = new ScheduledThreadPoolExecutor(1);
        service.scheduleAtFixedRate(getRunnable(), 1, 3, TimeUnit.SECONDS);
    }

    /**
     * repeat a runnable task and this method is
     * NOT an interval correct method.
     *
     * Any useful use case for the delay after
     * each runnable task before kicking off
     * this job again???
     */
    private static void scheduleWithFixedDelayTest() {
        ScheduledExecutorService service = new ScheduledThreadPoolExecutor(1);
        service.scheduleWithFixedDelay(getRunnable(), 1, 3, TimeUnit.SECONDS);
    }

    private static Runnable getRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                SleepUtil.sleepSec(5);
                if (timestamp.get() == 0) {
                    System.out.println("init timestamp");
                } else {
                    System.out.println("timestamp " + (System.currentTimeMillis() - timestamp.get()));
                }
                timestamp.set(System.currentTimeMillis());
            }
        };
    }


    private static void continueExistingPeriodicTasksAfterShutdownPolicyTest() {
        ScheduledThreadPoolExecutor service = new ScheduledThreadPoolExecutor(1);
        System.out.println(service.getContinueExistingPeriodicTasksAfterShutdownPolicy());
        service.scheduleAtFixedRate(getRunnable(), 1, 3, TimeUnit.SECONDS);

        service.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);

        SleepUtil.sleepSec(1);
        service.shutdown();
    }

    private static void executeExistingDelayedTasksAfterShutdownPolicyTest() {
        ScheduledThreadPoolExecutor service = new ScheduledThreadPoolExecutor(1);
        System.out.println(service.getExecuteExistingDelayedTasksAfterShutdownPolicy());
        service.scheduleWithFixedDelay(getRunnable(), 1, 3, TimeUnit.SECONDS);

        service.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
        //service.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);

        SleepUtil.sleepSec(12);
        service.shutdown();
    }

}
