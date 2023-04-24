package us.hcheng.javaio.thread.part2.chapter16.simple;

public class App {

    public static void main(String[] args) throws InterruptedException {
        CounterIncrement app = new CounterIncrement();
        app.start();

        Thread.sleep(300);
        app.shutdown();
    }

}
