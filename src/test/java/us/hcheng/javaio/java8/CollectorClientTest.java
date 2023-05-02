package us.hcheng.javaio.java8;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import us.hcheng.javaio.java8.entity.Apple;
import us.hcheng.javaio.java8.entity.Dish;
import us.hcheng.javaio.java8.features.CollectorClient;

import java.lang.constant.Constable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

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
    void groupByConcurrentWithFunctionTest() {
        Map<Dish.Type, List<Dish>> map = client.groupByConcurrentWithFunction(dishes);
        assertEquals(3, map.size());
        assertEquals(ConcurrentHashMap.class, map.getClass());
    }

    @Test
    void groupByConcurrentWithFunctionAndCollectorTest() {
        Map<Dish.Type, String> map = client.groupByConcurrentWithFunctionAndCollector(dishes);
        assertEquals(3, map.size());
        assertEquals(ConcurrentHashMap.class, map.getClass());
    }

    @Test
    void groupByConcurrentWithFunctionAndSupplierAndCollectorTest() {
        Map<String, Double> map = client.groupByConcurrentWithFunctionAndSupplierAndCollector(dishes);
        assertEquals(9, map.size());
        assertEquals(ConcurrentSkipListMap.class, map.getClass());
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

        assertThrows(UnsupportedOperationException.class, () -> m.remove(Dish.Type.MEAT));
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

    @Test
    void joiningInfoTest() {
        final String delimiter = "###";
        final String prefix = "PRE_PRE_PRE";
        final String suffix = "SUF_SUF_SUF";

        String s = client.joiningInfo(dishes);
        assertNotNull(s);

        assertEquals(dishes.size(), client.joiningInfo(dishes, delimiter).split(delimiter).length);

        s = client.joiningInfo(dishes, delimiter, prefix, suffix, Dish::getName);
        assertEquals(dishes.size(), s.split(delimiter).length);
        assertEquals(0, s.indexOf(prefix));
        assertEquals(s.length() - suffix.length(), s.indexOf(suffix));
    }

    @Test
    void mapInfoTest() {
        IntSummaryStatistics res = client.mapInfo(dishes);
        assertEquals(9, res.getCount());
        assertEquals(120, res.getMin());
        assertEquals(800, res.getMax());
    }

    @Test
    void minByTest() {
        assertFalse(client.minByCalorie(new ArrayList<>()).isPresent());

        Optional<Dish> optionalDish = client.minByCalorie(dishes);
        assertTrue(optionalDish.isPresent());
        assertEquals(120, optionalDish.get().getCalories());
    }

    @Test
    void maxByTest() {
        assertFalse(client.maxByCalorie(new ArrayList<>()).isPresent());

        Optional<Dish> optionalDish = client.maxByCalorie(dishes);
        assertTrue(optionalDish.isPresent());
        assertEquals(800, optionalDish.get().getCalories());
    }

    @Test
    void partitioningByHighCalorieTest() {
        Map<Boolean, List<Dish>> m = client.partitioningByHighCalorie(dishes);
        assertEquals(2, m.size());
        m.get(true).forEach(d -> assertTrue(d.getCalories() > 400));
    }

    @Test
    void partitioningByHighCalorieAndCollectTest() {
        Map<Boolean, Optional<Dish>> m = client.partitioningByHighCalorieAndCollectLowest(dishes);
        assertEquals(2, m.size());
        m.forEach((k, v) -> assertNotNull(m.get(k)));
        System.out.println(m);
    }

    @Test
    void maxCalorieByreducingTest() {
        Optional<Dish> optionalDish = client.maxCalorieByReducing(dishes);
        Dish dish = dishes.stream().reduce(BinaryOperator.maxBy(Comparator.comparingInt(Dish::getCalories)))
                .stream().limit(1).toList().get(0);

        assertTrue(optionalDish.isPresent());
        assertEquals(dish.getCalories(), optionalDish.get().getCalories());
    }

    @Test
    void sumCaloriesByReducingIdentityTest() {
        int sum = client.sumCaloriesByReducingIdentity(dishes);
        int expected = dishes.stream().mapToInt(Dish::getCalories).sum();
        assertEquals(expected, sum);
    }

    @Test
    void sumCaloriesByReducingIdentityAndFunctionTest() {
        int sum = client.sumCaloriesByReducingIdentityAndFunction(dishes);
        int expected = dishes.stream().mapToInt(Dish::getCalories).sum();
        assertEquals(expected, sum);
    }

    @Test
    void collectorSummarizingsTest() {
        IntSummaryStatistics intRes = client.caloriesInfoBySummarizingInt(dishes);
        LongSummaryStatistics longRes = client.caloriesInfoBySummarizingLong(dishes);
        DoubleSummaryStatistics doubleRes = client.caloriesInfoBySummarizingDouble(dishes);

        assertEquals(9L, intRes.getCount());
        assertEquals(120, intRes.getMin());
        assertEquals(800, intRes.getMax());

        assertEquals(9L, longRes.getCount());
        assertEquals(120L, longRes.getMin());
        assertEquals(800L, longRes.getMax());

        assertEquals(9L, doubleRes.getCount());
        assertEquals(120.0D, doubleRes.getMin());
        assertEquals(800.0D, doubleRes.getMax());
    }

    @Test
    void caloriesInfoBySummingsTest() {
        int resInt = client.caloriesInfoBySummingInt(dishes);
        long resLong = client.caloriesInfoBySummingInt(dishes);
        double resDouble = client.caloriesInfoBySummingInt(dishes);
        int target = dishes.stream().mapToInt(Dish::getCalories).sum();

        Stream.of(resInt, resLong, resDouble).forEach(each -> assertEquals(target, each.intValue()));

        resInt = client.caloriesInfoBySummingInt(new ArrayList<>());
        resLong = client.caloriesInfoBySummingInt(new ArrayList<>());
        resDouble = client.caloriesInfoBySummingInt(new ArrayList<>());

        assertEquals(0, resInt + resLong + resDouble);
    }

    @Test
    void vegetarianDishestoCollectionTest() {
        List<Dish> vegDishes = client.vegetarianDishesToCollection(dishes, LinkedList::new);
        long count = dishes.stream().filter(Dish::isVegetarian).count();

        assertEquals(count, vegDishes.size());
        assertEquals(LinkedList.class, vegDishes.getClass());

        vegDishes = client.vegetarianDishesToCollection(new ArrayList<>(), LinkedList::new);
        assertTrue(vegDishes.isEmpty());
    }

    @Test
    void getNameCalorieInfoToConcurrentMapTest() {
        Map<String, Integer> m = client.getNameCalorieInfoToConcurrentMap(dishes);
        assertEquals(9, m.size());
    }

    @Test
    void getNameCalorieInfoToConcurrentMapWithBinaryOperatorTest() {
        Map<Dish.Type, Long> m = client.getDishTypeCountToConcurrentMapWithBinaryOperator(dishes);
        assertEquals(3, m.size());
    }

    @Test
    void getDishTypeCountToConcurrentMapWithBinaryOperatorAndSupplierTest() {
        Map<Dish.Type, Long> m = client.getDishTypeCountToConcurrentMapWithBinaryOperatorAndSupplier(dishes, ConcurrentSkipListMap::new);
        assertEquals(3, m.size());
        assertEquals(ConcurrentSkipListMap.class, m.getClass());
    }

    @Test
    void getVegetarianDishesByToListTest() {
        List<Dish> list = client.getVegetarianDishesByToList(dishes);
        long size = dishes.stream().filter(Dish::isVegetarian).count();
        assertEquals(size, list.size());

        list = client.getVegetarianDishesByToList(new ArrayList<>());
        assertTrue(list.isEmpty());
    }

    @Test
    void getDishTypesByToSetTest() {
        Set<String> set = client.getDishTypesByToSet(dishes);
        assertEquals(3, set.size());

        set = client.getDishTypesByToSet(new ArrayList<>());
        assertTrue(set.isEmpty());
    }

    @Test
    void getDishCalorieInfoByToMapTest() {
        Map<String, Integer> map = client.getDishCalorieInfoByToMap(dishes);
        System.out.println(map);
        assertEquals(9, map.size());
    }

    @Test
    void getDishCalorieInfoByToMapWithBinaryOperatorTest() {
        Map<Boolean, Integer> m = client.getDishCalorieInfoByToMapWithBinaryOperator(dishes);
        assertEquals(2, m.size());
    }

    @Test
    void getDishCalorieInfoByToMapWithBinaryOperatorAndSupplierTest() {
        Map<Boolean, Integer> m = client.getDishCalorieInfoByToMapWithBinaryOperatorAndSupplier(dishes, Hashtable::new);
        assertEquals(2, m.size());
        assertEquals(Hashtable.class, m.getClass());
    }

}
