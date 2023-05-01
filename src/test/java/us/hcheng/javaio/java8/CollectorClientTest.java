package us.hcheng.javaio.java8;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import us.hcheng.javaio.java8.entity.Apple;
import us.hcheng.javaio.java8.entity.Dish;
import us.hcheng.javaio.java8.features.CollectorClient;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CollectorClientTest {

    private static CollectorClient client;
    private List<Apple> apples;
    private List<Dish> dishes;

    @BeforeAll
    static void init() {
        client = new CollectorClient();
    }

    @BeforeEach
    void setup() {
        apples = Arrays.asList(
                new Apple("green", 150), new Apple("yellow", 120),
                new Apple("green", 170), new Apple("green", 150),
                new Apple("yellow", 120), new Apple("green", 170));

        dishes = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH));
    }

    @Test
    void groupBysTest() {
        Map<String, List<Apple>> m1 = client.groupByNormal(apples);
        Map<String, List<Apple>> m2 = client.groupByFunctional(apples);
        Map<String, List<Apple>> m3 = client.groupByCollector(apples);

        assertEquals(m1.size(), m2.size());
        assertEquals(m2.size(), m3.size());
    }

    @Test
    void groupByCollectorFunctionAndCollectorTest() {
        Map<Dish.Type, Double> map = client.groupByCollectorFunctionAndCollector(dishes);
        System.out.println(map);
        assertEquals(3, map.size());
    }

    @Test
    void groupByFunctionAndSupplierAndCollectorTest() {
        Map<Dish.Type, Double> m = client.groupByFunctionAndSupplierAndCollector(dishes, TreeMap::new);
        assertEquals(3, m.size());
        assertEquals(TreeMap.class.getSimpleName(), m.getClass().getSimpleName());
    }

    @Test
    void averagesTest() {
        double val1 = client.averageCalorieInt(dishes);
        double val2 = client.averageCalorieLong(dishes);
        double val3 = client.averageCalorieDouble(dishes);

        assertEquals(val1, val2);
        assertEquals(val2, val3);
    }

    @Test
    void collectingAndThenTypeCountMapTest() {
        Map<Dish.Type, Integer> m = client.collectingAndThenTypeCountMap(dishes);
        assertEquals(3, m.size());
    }

    @Test
    void collectingAndThenAvgCaloriesStrTest() {
        String s = client.collectingAndThenAvgCaloriesStr(dishes);
        double val = client.averageCalorieInt(dishes);
        assertTrue(s.contains(String.valueOf(val)));
    }

    @Test
    void collectingAndThenTypeCountUnmodifiableMapTest() {
        Map<Dish.Type, List<Dish>> m = client.collectingAndThenTypeCountUnmodifiableMap(dishes);
        assertEquals(3, m.size());

        assertThrows(UnsupportedOperationException.class, () -> {
            m.remove(Dish.Type.MEAT);
        });

        m.get(Dish.Type.MEAT).clear();
        assertEquals(0, m.get(Dish.Type.MEAT).size());
    }

    @Test
    void countingTest() {
        assertEquals(9, client.counting(dishes));
    }

    @Test
    void summarizingIntCaloriesTest() {
        IntSummaryStatistics info = client.summarizingIntCalories(dishes);
        assertEquals(9, info.getCount());
        assertEquals(client.averageCalorieInt(dishes), info.getAverage());
    }

}
