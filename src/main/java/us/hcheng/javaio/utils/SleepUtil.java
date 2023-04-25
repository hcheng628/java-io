package us.hcheng.javaio.utils;

import java.sql.Time;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SleepUtil {

    private static final Random random = new Random(System.currentTimeMillis());

    public static void sleep(int time) {
        nap(time);
    }

    public static void sleepSecRandom() {
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(10));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void processing(Object tag) {
        int time = new Random().nextInt(10);
        if (tag == null)
            System.out.println(Thread.currentThread() + " processing time: " + time);
        else
            System.out.println(Thread.currentThread() + " processing tag: " + tag + " time: " + time);
        sleepSec(new Random().nextInt(10));
    }

    public static void processing() {
        processing(null);
    }


    public static void sleepSec(int sec) {
        try {
            TimeUnit.SECONDS.sleep(sec);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sleep() {
        nap(random.nextInt(1000));
    }

    private static void nap(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
