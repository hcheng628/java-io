package us.hcheng.javaio.java8.features;

import us.hcheng.javaio.java8.entity.Apple;
import us.hcheng.javaio.java8.entity.Dish;
import us.hcheng.javaio.java8.entity.SuperApple;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class StreamClient {

    public static class SuperAppleSupplier implements Supplier<SuperApple> {

        @Override
        public SuperApple get() {
            return new SuperApple(randStr(), ThreadLocalRandom.current().nextInt(100), randStr());
        }

    }

    public List<String> getLowCalorieDishes0(List<Dish> dishes) {
        List<Dish> lowCalorieDishes = new ArrayList<>();
        List<String> ret = new ArrayList<>();

        for (Dish d : dishes)
            if (d.getCalories() < 400)
                lowCalorieDishes.add(d);

        lowCalorieDishes.sort(Comparator.comparingInt(Dish::getCalories));

//        they are the same
//        lowCalorieDishes.sort((a, b) -> a.getCalories() - b.getCalories());
//        lowCalorieDishes.sort((a, b) -> Integer.compare(a.getCalories(), b.getCalories()));

        for (Dish d : lowCalorieDishes)
            ret.add(d.getName());

        return ret;
    }

    public List<String> getLowCalorieDishes(List<Dish> dishes) {
        return dishes.stream().parallel()
                .filter(d -> d.getCalories() < 400)
                .sorted(Comparator.comparingInt(Dish::getCalories))
                .map(Dish::getName)
                .toList();
    }

    public List<String> topCalorieDishes(List<Dish> dishes) {
        return dishes.stream().parallel()
                .filter(d -> d.getCalories() > 300)
                .sorted(Comparator.comparingInt(Dish::getCalories).reversed())
                .limit(3)
                .map(Dish::getName)
                .toList();
    }

    public <T> Stream<T> createStreamFromCollection(Collection<T> collection) {
        return collection.stream();
    }

    public <T> Stream<T> createStreamFromValues(T... args) {
        return Stream.of(args);
    }

    public <T> Stream<T> createStreamFromArray(T[] arr) {
        return Arrays.stream(arr);
    }

    public Stream<String> createStreamFromFile(String path) {
        Collection<String> ret = null;
        /**
         * Ideally, processing logic should be implemented not returned,
         * this way when stream is closed, the underline IO
         * resources are also closed.
         */
        try(Stream<String> stream = Files.lines(Paths.get(path))) {
            ret = stream.toList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret == null ? Stream.empty(): ret.stream();
    }

    public Stream<Long> createStreamFromIterate(int size) {
        return Stream.iterate(0L, aLong -> aLong + 2).limit(Math.max(1, size));
    }

    public Stream<Long> createStreamFromGenerate(int size) {
        return Stream.generate(() -> ThreadLocalRandom.current().nextLong()).limit(Math.max(1, size));
    }

    public Stream<Apple> createRandomApples(int size) {
        return Stream.generate(() -> new Apple(randStr(), new Random().nextLong())).limit(Math.max(1, size));
    }

    public static String randStr() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 10;
        return new Random().ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public Stream<SuperApple> createSuperAppleStreamFromGenerate(int size) {
        return Stream.generate(new SuperAppleSupplier()).limit(Math.max(1, size));
    }

}
