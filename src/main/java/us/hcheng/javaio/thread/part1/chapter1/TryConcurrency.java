package us.hcheng.javaio.thread.part1.chapter1;

import java.util.stream.IntStream;

public class TryConcurrency {

    public static void main(String[] args) {
        // 1. in order
        /*
        dbOperations();
        fileProcessing();
        */

        /*
        new Thread(()-> {
            IntStream.range(0, 100).
                    forEach( i -> {
                            try {
                                System.out.println(String.join(" - ", "A Task", String.valueOf(i)));
                                Thread.sleep(1000l);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
        }, "Moka").start();

        IntStream.range(0, 100).
                forEach( i -> {
                    try {
                        System.out.println(String.join(" - ", "B Task", String.valueOf(i)));
                        Thread.sleep(1000l);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
         */

        Thread t1 = new Thread(() -> {
            dbOperations();
        });
        Thread t2 = new Thread(() -> {
            fileProcessing();
        });

        t1.start();
        t2.start();
    }

    private static void dbOperations() {
        try {
            System.out.println("In DB Operations");
            Thread.sleep(1_000 * 1L);
            System.out.println("Process DB data...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("DB Operations Complete");
    }

    private static void fileProcessing() {
        try {
            System.out.println("In File Processing");
            Thread.sleep(1_000 * 1L);
            System.out.println("Process file...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("File Process Complete");
    }

}
