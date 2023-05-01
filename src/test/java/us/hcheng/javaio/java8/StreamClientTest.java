package us.hcheng.javaio.java8;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import us.hcheng.javaio.java8.entity.Apple;
import us.hcheng.javaio.java8.entity.Dish;
import us.hcheng.javaio.java8.entity.SuperApple;
import us.hcheng.javaio.java8.features.StreamClient;
import java.io.File;
import java.util.*;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

class StreamClientTest {

    private static List<Dish> dishes;
    private static StreamClient streamClient;

    @BeforeAll
    static void init() {
        dishes = new ArrayList<>();
        streamClient = new StreamClient();
    }

    @BeforeEach
    void setup() {
        dishes = new ArrayList<>(Stream.of(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH)).toList());
    }

    @AfterEach
    void tearDown() {
        dishes.clear();
    }

    @Test
    void getLowCalorieDishes0Test() {
        List<String> res = streamClient.getLowCalorieDishes0(dishes);
        assertEquals(3, res.size());
        assertEquals("season fruit", res.get(0));
    }

    @Test
    void getLowCalorieDishes() {
        List<String> res = streamClient.getLowCalorieDishes0(dishes);
        assertEquals(3, res.size());
        assertEquals("rice", res.get(2));
    }

    @Test
    void topCalorieDishesTest() {
        List<String> res = streamClient.topCalorieDishes(dishes);
        assertEquals(3, res.size());
        assertEquals("pork", res.get(0));
    }

    @Test
    void reConsumeStreamTest() {
        assertThrows(IllegalStateException.class, () -> {
            Stream<Dish> dishStream = dishes.stream();
            dishStream.toList();
            dishStream.toArray();
        });
    }

    @Test
    void createStreamFromCollectionTest() {
        Stream<String> stream = streamClient.createStreamFromCollection(Arrays.asList("Moka", "Cheng", "Apple"));
        assertEquals(3, stream.toList().size());
    }

    @Test
    void createStreamFromValuesTest() {
        Stream<String> stream = streamClient.createStreamFromValues("Moka", "Cheng", "Apple");
        assertEquals(3, stream.toList().size());
    }

    @Test
    void createStreamFromArrayTest() {
        Stream<String> stream = streamClient.createStreamFromArray(new String[]{"Moka", "Cheng", "Apple"});
        assertEquals(3, stream.toList().size());
    }

    @Test
    void createStreamFromFileTest() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("test.properties").getFile());
        Stream<String> stream = streamClient.createStreamFromFile(file.getAbsolutePath());
        assertEquals(3, stream.toList().size());
    }

    @Test
    void createStreamFromIterateTest() {
        final int size = 10;
        Stream<Long> stream = streamClient.createStreamFromIterate(size);
        List<Long> list = stream.toList();

        assertEquals(size, list.size());
        list.forEach(i -> assertTrue(i % 2 == 0));
    }

    @Test
    void createStreamFromGenerateTest() {
        final int size = 100;
        Stream<Long> stream = streamClient.createStreamFromGenerate(size);
        List<Long> list = stream.toList();
        assertEquals(size, list.size());
    }

    @Test
    void createRandomApplesTest() {
        final int size = 100;
        Stream<Apple> stream = streamClient.createRandomApples(size);
        List<Apple> list = stream.toList();
        System.out.println(list);
        assertEquals(size, list.size());
    }

    @Test
    void createObjStreamFromGenerateTest() {
        final int size = 100;
        Stream<SuperApple> stream = streamClient.createSuperAppleStreamFromGenerate(size);
        List<SuperApple> list = stream.toList();
        System.out.println(list);
        assertEquals(size, list.size());
    }

    @Test
    void twiceCollectionTest() {
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        List<Integer> list = streamClient.twiceCollection(arr).toList();

        for (int i = 0, len = arr.length; i < len; i++)
            assertEquals(list.get(i), arr[i] + arr[i]);
    }

    @Test
    void uniqueLettersFromStringArrayTest() {
        String[] arr = new String[]{"Cheng", "Moka", "Apple"};
        Set<Character> set = new HashSet<>();

        Arrays.stream(arr).forEach(w -> w.chars().forEach(i -> set.add((char) i)));
        streamClient.uniqueLettersFromStringArray(arr).forEach(each -> assertTrue(set.contains(each)));
    }


    @Test
    void matchesTest() {
        assertTrue(streamClient.noneMatchDishes(dishes, d -> d.getCalories() > 900));
        assertTrue(streamClient.anyMatchDish(dishes, d -> d.getCalories() > 0));
        assertTrue(streamClient.allMatchDishes(dishes, d -> d.getCalories() > 0));
    }

    @Test
    void sumCaloriesTest() {
        int res = streamClient.sumCalories(dishes, Dish::isVegetarian);
        assertEquals(1550, res);
    }

    @Test
    void Test() {
        Dish[] info = streamClient.getCaloriesRanges(dishes, d -> true);

        assertNotNull(info);
        assertEquals(120, info[0].getCalories());
        assertEquals(800, info[1].getCalories());
    }

    @Test
    void sumIntegerByIntStreamTest() {
        List<Integer> list = dishes.stream().filter(Dish::isVegetarian).map(Dish::getCalories).toList();
        assertEquals(1550, streamClient.sumIntegerByIntStream(list));
    }

    @Test
    void pythagoreanTheoremTest() {
        List<int[]> res = streamClient.pythagoreanTheorem(9, 100);
        assertEquals(2, res.size());
    }

}
