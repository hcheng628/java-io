package us.hcheng.javaio.thread.jcu.executor;

import us.hcheng.javaio.utils.SleepUtil;
import java.util.concurrent.*;

public class ScheduledExecutorServiceExample1 {

    public static void main(String[] args) {
        //scheduleRunnableTest();
        scheduleCallableTest();
    }

    private static void scheduleRunnableTest() {
        ScheduledExecutorService service = new ScheduledThreadPoolExecutor(1);
        Future<?> future = service.schedule(new Runnable() {
            @Override
            public void run() {
                SleepUtil.sleepSec(3);
                System.out.println(System.currentTimeMillis());
            }
        }, 1L, TimeUnit.SECONDS);

        SleepUtil.sleepSec(4);
        try {
            // always null as runnable
            System.out.println(future.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void scheduleCallableTest() {
        ScheduledExecutorService service = new ScheduledThreadPoolExecutor(1);
        Future<?> future = service.schedule(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                SleepUtil.sleepSec(3);
                System.out.println(System.currentTimeMillis());
                return 13;
            }
        }, 1L, TimeUnit.SECONDS);

        SleepUtil.sleepSec(4);
        try {
            System.out.println(future.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
