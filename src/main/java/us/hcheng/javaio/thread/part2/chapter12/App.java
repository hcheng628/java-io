package us.hcheng.javaio.thread.part2.chapter12;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class App {

    public static void main(String[] args) throws InterruptedException {
        String path = "file.txt"; // unused
        BalkingData data = new BalkingData(path, "Moka");
        List<Thread> threads = new ArrayList<>();

        Stream.of("Client1", "Client2", "Client3").forEach(each -> {
            threads.add(
                    new Thread(() -> {
                        for (int i = 0; i < 10; i++) {
                            try {
                                Thread.sleep(new Random().nextInt(1000));
                                data.update(each);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }, each)
            );
        });

        Stream.of("Server1", "Server2", "Server3", "Server4", "Server5").forEach(each -> {
            threads.add(
                    new Thread(()->{
                        for (int i = 0; i < 10; i++) {
                            try {
                                Thread.sleep(new Random().nextInt(1000));
                                data.save();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }, each)
            );
        });

        threads.forEach(Thread::start);
        for (Thread thread : threads)
            thread.join();

        System.out.println("THE END");
    }

}
