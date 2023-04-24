package us.hcheng.javaio.thread.part2.chapter7;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.IntStream;

public class App {


    public static void main(String[] args) throws InterruptedException {
        PersonImmutable person1 = new PersonImmutable("Moka", "Atlanta");
        PersonImmutable person2 = new PersonImmutable("Cheng", "Shijiazhuang");
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);

        IntStream.range(0, 2).forEach(i -> {
            executor.submit(new PersonRunnable(i == 0 ? person1 : person2));
        });

        Thread.sleep(10);
        executor.shutdownNow();
    }

}
