package us.hcheng.javaio.thread.part2.chapter13.service;

import us.hcheng.javaio.thread.part2.chapter13.entity.Job;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer extends Thread {

    private final IMessageBus<String> messageBus;
    private final AtomicInteger seq;

    public Producer(String id, IMessageBus<String> messageBus) {
        super(String.join("-", "Producer", id));
        this.messageBus = messageBus;
        this.seq = new AtomicInteger(0);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(new Random().nextInt(300));
                String name = String.join("-", Thread.currentThread().getName(), String.valueOf(seq.getAndIncrement()));
                this.messageBus.put(new Job<>(name, each -> {
                    String msg = String.join("-", Thread.currentThread().getName(), " consume ", each);
                    System.out.println(msg);
                }));
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
