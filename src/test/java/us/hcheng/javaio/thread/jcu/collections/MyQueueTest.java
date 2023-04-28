package us.hcheng.javaio.thread.jcu.collections;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class MyQueueTest {

    private static MyQueue<String> queue;

    @BeforeAll
    static void init() {
        queue = new MyQueue<>();
    }

    @BeforeEach
    void setup() {
        queue.addLast("Hello");
        queue.addLast("World");
        queue.addLast("Java");
    }

    @AfterEach
    void tearDown() {
        queue.clear();
    }

    @Test
    void removeTest() {
        Stream.of("Hello", "World", "Java").forEach(w -> assertEquals(w, queue.removeFirst()));
    }

}
