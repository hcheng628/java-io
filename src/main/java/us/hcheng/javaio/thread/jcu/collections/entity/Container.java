package us.hcheng.javaio.thread.jcu.collections.entity;

import lombok.Data;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

@Data
public class Container<T> implements Delayed {

    private T data;

    private final long readyTimestamp;

    public static <T> Container<T> of(T data, long delay) {
        return new Container<>(data, delay);
    }

    private Container(T data, long delay) {
        this.data = data;
        this.readyTimestamp = System.currentTimeMillis() + delay;
    }

    public T getData() {
        return data;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(readyTimestamp - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        if (getDelay(TimeUnit.MILLISECONDS) < o.getDelay(TimeUnit.MILLISECONDS))
            return -1;
        else if (getDelay(TimeUnit.MILLISECONDS) == o.getDelay(TimeUnit.MILLISECONDS))
            return 0;
        return 1;
    }

    @Override
    public String toString() {
        return "Container{" +
                "data=" + data +
                ", readyTimestamp=" + readyTimestamp +
                '}';
    }

}
