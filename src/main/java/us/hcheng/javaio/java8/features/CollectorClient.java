package us.hcheng.javaio.java8.features;

import us.hcheng.javaio.java8.entity.Apple;
import us.hcheng.javaio.java8.entity.Dish;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class CollectorClient {

    public Map<String, List<Apple>> groupByNormal(List<Apple> apples) {
        Map<String, List<Apple>> ret = new HashMap<>();

        for (Apple apple : apples) {
            if (apple != null && apple.getColor() != null) {
                List<Apple> list = ret.computeIfAbsent(apple.getColor(), k -> new ArrayList<>());
                list.add(apple);
            }
        }

        return ret;
    }

    public Map<String, List<Apple>> groupByFunctional(List<Apple> apples) {
        Map<String, List<Apple>> ret = new HashMap<>();

        apples.forEach(apple -> {
            if (apple != null && apple.getColor() != null) {
                List<Apple> list = ret.computeIfAbsent(apple.getColor(), k -> new ArrayList<>());
                list.add(apple);
            }
        });

        return ret;
    }

    public Map<String, List<Apple>> groupByCollector(List<Apple> apples) {
        return apples.stream().collect(groupingBy(Apple::getColor));
    }

    public Map<Dish.Type, Double> groupByCollectorFunctionAndCollector(List<Dish> dishes) {
        return dishes.stream().collect(Collectors.groupingBy(
                Dish::getType, Collectors.averagingInt(Dish::getCalories)));
    }
    public Map<Dish.Type, Double> groupByFunctionAndSupplierAndCollector(List<Dish> dishes, Supplier<Map<Dish.Type, Double>> supplier) {
        return dishes.stream().collect(Collectors.groupingBy(
                Dish::getType, supplier, Collectors.averagingInt(Dish::getCalories)
        ));
    }

    public Double averageCalorieDouble(List<Dish> dishes) {
        return dishes.stream().collect(Collectors.averagingDouble(Dish::getCalories));
    }

    public Double averageCalorieInt(List<Dish> dishes) {
        return dishes.stream().collect(Collectors.averagingInt(Dish::getCalories));
    }

    public Double averageCalorieLong(List<Dish> dishes) {
        return dishes.stream().collect(Collectors.averagingInt(Dish::getCalories));
    }

    public Map<Dish.Type, Integer> collectingAndThenTypeCountMap(List<Dish> dishes) {
        return dishes.stream()
                .collect(Collectors.collectingAndThen(
                        groupingBy(Dish::getType), map -> {
                            Map<Dish.Type, Integer> ret = new HashMap<>();
                            map.forEach((k, list) -> ret.put(k, list.size()));
                            return ret;
                        }));
    }

    public String collectingAndThenAvgCaloriesStr(List<Dish> dishes) {
        return dishes.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.averagingInt(Dish::getCalories),
                        avg -> String.join(":", "Average Calorie", String.valueOf(avg))
                ));
    }

    public Map<Dish.Type, List<Dish>> collectingAndThenTypeCountUnmodifiableMap(List<Dish> dishes) {
        return dishes.stream()
                .collect(Collectors.collectingAndThen(groupingBy(Dish::getType), Collections::unmodifiableMap));
    }

    public long counting(List<Dish> dishes) {
        return dishes.stream().collect(Collectors.counting());
    }

    public IntSummaryStatistics summarizingIntCalories(List<Dish> dishes) {
        return dishes.stream().collect(Collectors.summarizingInt(Dish::getCalories));
    }

}
