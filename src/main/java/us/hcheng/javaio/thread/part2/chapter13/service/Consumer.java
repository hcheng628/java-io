package us.hcheng.javaio.thread.part2.chapter13.service;

import us.hcheng.javaio.thread.part2.chapter13.entity.Job;

import java.util.Random;

public class Consumer extends Thread {

    private final IMessageBus<String> messageBus;
    private final String id;

    public Consumer(String id, IMessageBus<String> messageBus) {
        super(String.join("-", "Consumer", id));
        this.id = id;
        this.messageBus = messageBus;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(new Random().nextInt(1000));
                Job<String> job = this.messageBus.take();
                job.getConsumer().accept(job.toString());
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
