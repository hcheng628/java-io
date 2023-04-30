package us.hcheng.javaio.java8;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import us.hcheng.javaio.java8.entity.Apple;
import us.hcheng.javaio.java8.service.FilterApple;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FilterAppleTest {

    private FilterApple filter;
    private List<Apple> apples;

    @BeforeEach
    void setup() {
        filter = new FilterApple();
        apples = Stream.of(new Apple("green", 150),
                new Apple("yellow", 120),
                new Apple("green", 170)).toList();
    }

    @Test
    void filterGreenV1Test() {
        List<Apple> greenApples = filter.filterGreenApple(apples);
        assertEquals(2, greenApples.size());
    }

    @Test
    void filterGreenV2Test() {
        List<Apple> greenApples = filter.filterGreenApple(apples, "green");
        assertEquals(2, greenApples.size());
    }

    @Test
    void filterGreenV3Test() {
        List<Apple> greenApples = filter.filterGreenApple(apples, (FilterApple.AppleFilter) apple -> "green".equals(apple.getColor()));
        assertEquals(2, greenApples.size());
    }

    @Test
    void filterGreenV4Test() {
        List<Apple> greenApples = filter.filterGreenApple(apples, (Predicate<Apple>) a -> "green".equals(a.getColor()));
        assertEquals(2, greenApples.size());
    }

    @Test
    void filterAppleByWeightTest() {
        List<Apple> res = filter.filterAppleByWeight(apples, w -> w > 150);
        assertEquals(1, res.size());
    }

    @Test
    void filterAppleByBiPredicateTest() {
        List<Apple> res = filter.filterAppleByBiPredicate(apples, (color, w) -> color.equals("green") && w > 150);
        assertEquals(1, res.size());
    }

    @Test
    void consumeApplesTest() {
        final AtomicInteger count = new AtomicInteger();
        filter.consumeApples(apples, a -> {
            count.getAndIncrement();
            System.out.println("*** eat this apple -> " + a + " ***");
        });

        assertEquals(3, count.get());
    }//biConsumeApples

    @Test
    void biConsumeApplesTest() {
        final AtomicInteger count = new AtomicInteger();
        filter.biConsumeApples(apples, new Date(), (a, date) -> {
            count.getAndIncrement();
            System.out.println("*** eat this apple -> " + a + " *** at " + date);
        });

        assertEquals(3, count.get());
    }

    @Test
    void functionApplesTest() {
        String res = filter.functionApples(apples, apple -> apple.getColor());
        assertEquals(3, res.split(";").length);
        res = filter.functionApples(apples, apple -> String.valueOf(apple.getWeight()));
        assertEquals(3, res.split(";").length);
    }

    @Test
    void biFunctionApplesTest() {
        apples.stream().forEach(a ->
            assertNotNull(filter.biFunctionApples(a, ThreadLocalRandom.current().nextLong(), (apple, val) ->
                    String.valueOf(String.valueOf(Math.abs(apple.getWeight() - val)).hashCode()))
            ));
    }

}
