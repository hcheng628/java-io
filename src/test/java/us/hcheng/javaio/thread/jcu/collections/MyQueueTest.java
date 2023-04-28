package us.hcheng.javaio.thread.jcu.collections;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MyQueueTest {

    private static MyQueue<String> queue;
    private static MyQueue<String> queueSafe;

    @BeforeAll
    static void init() {
        queue = new MyQueue<>();
        queueSafe = new MyQueue<>(true);
    }

    @BeforeEach
    void setup() {
        Stream.of("Hello", "World", "Java").forEach(w ->
            Stream.of(queue, queueSafe).forEach(q -> q.addLast(w))
        );
    }

    @AfterEach
    void tearDown() {
        Stream.of(queue, queueSafe).forEach(MyQueue::clear);
    }

    @Test
    void removeTest() {
        Stream.of("Hello", "World", "Java").forEach(w ->
                Stream.of(queue, queueSafe).forEach(q -> assertEquals(w, q.removeFirst()))
        );
    }

}
