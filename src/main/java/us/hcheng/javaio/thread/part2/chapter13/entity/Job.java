package us.hcheng.javaio.thread.part2.chapter13.entity;

import java.util.function.Consumer;

public class Job<T> {

    private String id;
    private Consumer<T> consumer;

    public Job(String id, Consumer<T> consumer) {
        this.id = id;
        this.consumer = consumer;
    }

    public String getId() {
        return id;
    }

    public Consumer<T> getConsumer() {
        return consumer;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id='" + id +
                '}';
    }

}
