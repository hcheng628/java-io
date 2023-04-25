package us.hcheng.javaio.thread.jcu.executor;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class SchedulerTests {

    /**
     * Schedulers:
     *  1. Timer|TimerTask              [Not interval correct]
     *  2. SchedulerExecutorService
     *  3. crontab                      [Interval correct]
     *  4. cron4j
     *  5. quartz                       [Interval correct]
     *
     *
     *  Potential issues with [Interval correct], we could pile up too many jobs that
     *  host cannot process and eventually run out of resources
     */

    public static void main(String[] args) {
        //timerTaskTest();
        quartzTest();
    }

    /**
     * If job takes longer than wait period, it will not be fired in that fixed interval
     */
    private static void timerTaskTest() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("timerTask run method ...... " + System.currentTimeMillis());
            }
        };
        timer.schedule(timerTask, 1000, 1000);
    }

    private static void quartzTest() {
        String group = "G1";
        JobDataMap dataMap = new JobDataMap();
        dataMap.put("date", new Date());
        JobDetail jobDetail = JobBuilder.newJob(QuartzJob.class)
                .usingJobData("jobSays", "Hello World!")
                .usingJobData("myFloatValue", 3.141f)
                .usingJobData(dataMap)
                .withIdentity("JobName-id-quartzTest", group)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("t1", group)
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(1)
                        .repeatForever())
                .build();

        try {
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
            System.err.println("job kicked off...");
        } catch (SchedulerException e) {
            System.err.println("SchedulerException: " + e);
        }
    }

    public static class QuartzJob implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            JobDataMap dataMap = context.getJobDetail().getJobDataMap();
            String jobSays = dataMap.getString("jobSays");
            float myFloatValue = dataMap.getFloat("myFloatValue");
            StringBuilder sb = new StringBuilder();
            sb.append(jobSays).append(myFloatValue).append(dataMap.getWrappedMap().get("date"));
            System.err.println("QuartzJob execute method ...... " +
                    System.currentTimeMillis() + "\n" + sb);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
