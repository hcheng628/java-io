package us.hcheng.javaio.thread.part2.chapter13;

import us.hcheng.javaio.thread.part2.chapter13.service.Consumer;
import us.hcheng.javaio.thread.part2.chapter13.service.MessageBus;
import us.hcheng.javaio.thread.part2.chapter13.service.Producer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class App {

    public static void main(String[] args) {
        MessageBus<String> messageBus = new MessageBus<>();
        List<Thread> threads = new ArrayList<>();

        IntStream.range(0, 2).forEach(i -> {
            threads.add(new Consumer(String.valueOf(i), messageBus));
        });

        IntStream.range(0, 3).forEach(i -> {
            threads.add(new Producer(String.valueOf(i), messageBus));
        });

        threads.forEach(Thread::start);

        new Thread(()-> {
            IntStream.range(0, 10).forEach(i -> {
                try {
                    Thread.sleep(new Random().nextInt(2000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.err.println(messageBus.currQueueSize() + "\n" +
                        messageBus + "\n");
            });
        }).start();
    }

}
