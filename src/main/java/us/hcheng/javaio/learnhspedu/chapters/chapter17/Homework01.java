package us.hcheng.javaio.learnhspedu.chapters.chapter17;

import us.hcheng.javaio.utils.SleepUtil;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Homework01 {

    private static boolean running = true;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (running) {
                System.out.println(ThreadLocalRandom.current().nextInt(100));
                SleepUtil.sleepSec(3);
            }
        });

        Thread t2 = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (running) {
                System.err.print("Enter Q to stop: ");
                String s = scanner.nextLine();
                if (s != null && s.toUpperCase().charAt(0) == 'Q') {
                    running = false;
                }
            }
        });

        t1.start();
        t2.start();
    }

}
