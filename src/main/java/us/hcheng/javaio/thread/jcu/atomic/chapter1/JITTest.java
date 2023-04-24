package us.hcheng.javaio.thread.jcu.atomic.chapter1;

public class JITTest {

    private static boolean terminated = false;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (!terminated) {
                System.out.println("If there is no such statement it will execute forever ;-) or use volatile");
            }
        }).start();

        Thread.sleep(1_000);

        new Thread(() -> {
            terminated = true;
        }).start();
    }
}
