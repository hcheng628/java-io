package us.hcheng.javaio.java8.service;

import us.hcheng.javaio.java8.entity.Apple;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.*;

public class FilterApple {

    public interface AppleFilter {
        boolean filter(Apple apple);
    }

    public List<Apple> filterGreenApple(List<Apple> apples) {
        if (apples == null)
            throw new IllegalArgumentException();

        List<Apple> ret = new ArrayList<>();

        for (Apple a : apples)
            if ("green".equals(a.getColor()))
                ret.add(a);

        return ret;
    }

    public List<Apple> filterGreenApple(List<Apple> apples, String color) {
        if (apples == null || color == null)
            throw new IllegalArgumentException();

        List<Apple> ret = new ArrayList<>();

        for (Apple a : apples)
            if (color.equals(a.getColor()))
                ret.add(a);

        return ret;
    }

    public List<Apple> filterGreenApple(List<Apple> apples, AppleFilter appleFilter) {
        if (apples == null || appleFilter == null)
            throw new IllegalArgumentException();

        return apples.stream().filter(a -> appleFilter.filter(a)).toList();
    }

    public List<Apple> filterGreenApple(List<Apple> apples, Predicate<Apple> predicate) {
        if (apples == null || predicate == null)
            throw new IllegalArgumentException();

        return apples.stream().filter(predicate).toList();
    }

    public List<Apple> filterAppleByWeight(List<Apple> apples, LongPredicate predicate) {
        if (apples == null || predicate == null)
            throw new IllegalArgumentException();

        return apples.stream().filter(a -> predicate.test(a.getWeight())).toList();
    }

    public List<Apple> filterAppleByBiPredicate(List<Apple> apples, BiPredicate<String, Long> predicate) {
        if (apples == null || predicate == null)
            throw new IllegalArgumentException();

        return apples.stream().filter(a -> predicate.test(a.getColor(), a.getWeight())).toList();
    }

    public void consumeApples(List<Apple> apples, Consumer<Apple> consumer) {
        if (apples == null || consumer == null)
            throw new IllegalArgumentException();

        apples.stream().forEach(consumer::accept);
    }

    public void biConsumeApples(List<Apple> apples, Date date, BiConsumer<Apple, Date> consumer) {
        if (apples == null || consumer == null)
            throw new IllegalArgumentException();
        apples.stream().forEach(a -> consumer.accept(a, date == null ? new Date() : date));
    }

    public String functionApples(List<Apple> apples, Function<Apple, String> fun) {
        if (apples == null || fun == null)
            throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder();
        apples.stream().forEach(a -> sb.append(fun.apply(a)).append(';'));
        return sb.toString();
    }

    public String biFunctionApples(Apple apple, long val, BiFunction<Apple, Long, String> fun) {
        if (apple == null || fun == null)
            throw new IllegalArgumentException();

        return fun.apply(apple, val);
    }

}
