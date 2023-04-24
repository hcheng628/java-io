package us.hcheng.javaio.thread.part1.chapter6;

public class ThreadInterrupt {

    private static final Object MONITOR = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread("t1"){
            @Override
            public void run() {
                while (true) {
                    /*
                    // 1. 线程状态改变，不会捕获到打断信号
                    System.out.println(">>" + isInterrupted());
                    if (this.isInterrupted())
                        break;

                    // 2. sleep().线程状态改变，且捕获到打断信号
                    try {
                        Thread.sleep(1_000);
                    } catch (InterruptedException e) {
                        System.out.println("收到打断信号。");
                        e.printStackTrace();
                    }
                    */

                    // 3. wait().线程状态改变，且捕获到打断信号
                    synchronized (MONITOR) {
                        try {
                            MONITOR.wait(1_000);
                        } catch (InterruptedException ex) {
                            System.out.println("wait()->" + isInterrupted());
                            break;
                        }
                    }
                }

                System.out.println("t1 completed...");
            }
        };

        t1.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        System.out.println("before t1.isInterrupted(): " + t1.isInterrupted());
        t1.interrupt();
        System.out.println("after t1.isInterrupted(): " + t1.isInterrupted());

        Thread t2 = new Thread(() -> {
            while (true) {
                synchronized (MONITOR) {
                    try {
                        MONITOR.wait(1_000);
                        System.out.println("t2 processing");
                    } catch (InterruptedException e) {
                        // 获取不到
                        // System.out.println("wait()->" + isInterrupted());
                        System.out.println("wait()->" + Thread.interrupted());
                        e.printStackTrace();
                        break;
                    }
                }
                System.out.println("t2 completed...");
            }
        }, "t2");

        t2.start();

        Thread t3 = new Thread(() -> {
            while (true) {

            }
        }, "t3");
        t3.start();

        Thread main = Thread.currentThread();
        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            main.interrupt();
        }).start();

        try {
            // 这里join的不是t3线程，而是main线程
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("main complete");
    }

}
