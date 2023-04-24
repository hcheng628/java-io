package us.hcheng.javaio.thread.part2.chapter3;

public class InterruptTest {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(()->{
            while (true) {
                System.out.println("haha" + Thread.currentThread().isInterrupted());
            }
        });

        t.start();
        Thread.sleep(1000);
        t.interrupt();
    }
}
