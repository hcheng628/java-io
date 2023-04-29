package us.hcheng.javaio.thread.jcu.collections;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import us.hcheng.javaio.thread.jcu.collections.custom.SimpleSkipList;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SimpleSkipListTest {

    private static SimpleSkipList skipList;

    @BeforeAll
    static void init() {
        skipList = new SimpleSkipList();
    }

    @Test
    void test() {
        Set<Integer> addedNumbers = new HashSet<>();
        List<Integer> nonExistNumbers = new ArrayList<>();

        IntStream.range(0, 50).forEach(i -> addedNumbers.add(ThreadLocalRandom.current().nextInt(200)));

        while (nonExistNumbers.size() < 5) {
            int num = ThreadLocalRandom.current().nextInt(200);
            if (!addedNumbers.contains(num))
                nonExistNumbers.add(num);
        }

        addedNumbers.forEach(i -> skipList.add(i));
        addedNumbers.forEach(i -> assertTrue(skipList.contains(i)));
        nonExistNumbers.forEach(i -> assertFalse(skipList.contains(i)));

        skipList.dumpList();
    }

}
