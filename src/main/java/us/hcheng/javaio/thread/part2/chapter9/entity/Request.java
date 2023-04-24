package us.hcheng.javaio.thread.part2.chapter9.entity;

public final class Request<T> {

    private final T value;

    public Request(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

}
