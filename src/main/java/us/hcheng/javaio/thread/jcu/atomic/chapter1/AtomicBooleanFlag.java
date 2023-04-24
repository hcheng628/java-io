package us.hcheng.javaio.thread.jcu.atomic.chapter1;

import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicBooleanFlag {

    private static AtomicBoolean flag = new AtomicBoolean(true);
    private static boolean booleanFlag = true;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            try {
                while (flag.get()) {
                    Thread.sleep(1_000);
                    System.out.println("Happy AtomicBoolean!");
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                while (booleanFlag) {
                    Thread.sleep(1_000);
                    System.out.println("Happy boolean!");
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(3_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            flag.set(false);
            booleanFlag = false;    // it stops it but above approach is safer!
        }).start();
    }




}
