package us.hcheng.javaio.thread.part2.chapter15.entity;

public class Message {

    private String content;

    public Message(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "content='" + content + "\' By " +
                Thread.currentThread().getName() +
                '}';
    }
}
