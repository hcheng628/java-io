package us.hcheng.javaio.java8.features;

import us.hcheng.javaio.java8.entity.Apple;
import us.hcheng.javaio.java8.entity.Dish;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;
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

    public Map<Dish.Type, List<Dish>> groupByConcurrentWithFunction(List<Dish> dishes) {
        return dishes.stream().collect(Collectors.groupingByConcurrent(Dish::getType));
    }

    public Map<Dish.Type, String> groupByConcurrentWithFunctionAndCollector(List<Dish> dishes) {
        return dishes.stream().collect(Collectors.groupingByConcurrent(
                Dish::getType,
                Collectors.collectingAndThen(Collectors.averagingInt(Dish::getCalories),
                        res -> String.join(":", "Avg", String.valueOf(res)))));
    }

    public Map<String, Double> groupByConcurrentWithFunctionAndSupplierAndCollector(List<Dish> dishes) {
        return dishes.stream().collect(Collectors.groupingByConcurrent(
                Dish::getName, ConcurrentSkipListMap::new, Collectors.averagingInt(Dish::getCalories)
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

    public String joiningInfo(List<Dish> dishes) {
        return dishes.stream().map(Dish::toString).collect(Collectors.joining());
    }

    public String joiningInfo(List<Dish> dishes, String delimiter) {
        return dishes.stream().map(Dish::toString).collect(Collectors.joining(delimiter));
    }

    public String joiningInfo(List<Dish> dishes, String delimiter, String prefix, String suffix, Function<Dish, String> func) {
        return dishes.stream().map(Dish::toString).collect(Collectors.joining(delimiter, prefix, suffix));
    }

    public IntSummaryStatistics mapInfo(List<Dish> dishes) {
        return dishes.stream().collect(Collectors.mapping(Dish::getCalories, Collectors.summarizingInt(value -> value)));
    }

    public Optional<Dish> maxByCalorie(List<Dish> dishes) {
        return dishes.stream().collect(Collectors.maxBy(Comparator.comparing(Dish::getCalories)));
    }

    public Optional<Dish> minByCalorie(List<Dish> dishes) {
        return dishes.stream().collect(Collectors.minBy(Comparator.comparing(Dish::getCalories)));
    }

    public Map<Boolean, List<Dish>> partitioningByHighCalorie(List<Dish> dishes) {
        return dishes.stream().collect(Collectors.partitioningBy(d -> d.getCalories() > 400));
    }

    public Map<Boolean, Optional<Dish>> partitioningByHighCalorieAndCollectLowest(List<Dish> dishes) {
        return dishes.stream().collect(Collectors.partitioningBy(
                d -> d.getCalories() > 400,
                Collectors.minBy(Comparator.comparingInt(Dish::getCalories))));
    }

    public Optional<Dish> maxCalorieByReducing(List<Dish> dishes) {
        return dishes.stream().collect(Collectors.reducing(BinaryOperator.maxBy(Comparator.comparingInt(Dish::getCalories))));
    }

    public int sumCaloriesByReducingIdentity(List<Dish> dishes) {
        return dishes.stream().map(Dish::getCalories).collect(Collectors.reducing(0, (ret, each) -> ret + each));
    }

    public int sumCaloriesByReducingIdentityAndFunction(List<Dish> dishes) {
        return dishes.stream().collect(Collectors.reducing(0, Dish::getCalories, (ret, each) -> ret + each));
    }

    public IntSummaryStatistics caloriesInfoBySummarizingInt(List<Dish> dishes) {
        return dishes.stream().collect(Collectors.summarizingInt(Dish::getCalories));
    }

    public LongSummaryStatistics caloriesInfoBySummarizingLong(List<Dish> dishes) {
        return dishes.stream().collect(Collectors.summarizingLong(Dish::getCalories));
    }

    public DoubleSummaryStatistics caloriesInfoBySummarizingDouble(List<Dish> dishes) {
        return dishes.stream().collect(Collectors.summarizingDouble(Dish::getCalories));
    }


    public int caloriesInfoBySummingInt(List<Dish> dishes) {
        return dishes.stream().collect(Collectors.summingInt(Dish::getCalories));
    }

    public long caloriesInfoBySummingLong(List<Dish> dishes) {
        return dishes.stream().collect(Collectors.summingLong(Dish::getCalories));
    }

    public double caloriesInfoBySummingDouble(List<Dish> dishes) {
        return dishes.stream().collect(Collectors.summingDouble(Dish::getCalories));
    }

    public List<Dish> vegetarianDishesToCollection(List<Dish> dishes, Supplier<List<Dish>> supplier) {
        return dishes.stream().filter(Dish::isVegetarian).collect(Collectors.toCollection(supplier));
    }

    public Map<String, Integer> getNameCalorieInfoToConcurrentMap(List<Dish> dishes) {
        return dishes.stream().collect(Collectors.toConcurrentMap(
                Dish::getName,
                Dish::getCalories));
    }

    public Map<Dish.Type, Long> getDishTypeCountToConcurrentMapWithBinaryOperator(List<Dish> dishes) {
        return dishes.stream().collect(Collectors.toConcurrentMap(
                Dish::getType,
                dish -> 1L,
                Long::sum
        ));
    }

    public Map<Dish.Type, Long> getDishTypeCountToConcurrentMapWithBinaryOperatorAndSupplier(List<Dish> dishes, Supplier<ConcurrentMap<Dish.Type, Long>> supplier) {
        return dishes.stream().collect(Collectors.toConcurrentMap(
                Dish::getType,
                dish -> 1L,
                Long::sum,
                supplier
        ));
    }

    public List<Dish> getVegetarianDishesByToList(List<Dish> dishes) {
        return dishes.stream().filter(Dish::isVegetarian).collect(Collectors.toList());
    }

    public Set<String> getDishTypesByToSet(List<Dish> dishes) {
        return dishes.stream().map(d -> d.getType().toString()).collect(Collectors.toSet());
    }

    public Map<String, Integer> getDishCalorieInfoByToMap(List<Dish> dishes) {
        return dishes.stream().collect(Collectors.toMap(
                Dish::getName,
                Dish::getCalories
        ));
    }

    public Map<Boolean, Integer> getDishCalorieInfoByToMapWithBinaryOperator(List<Dish> dishes) {
        return dishes.stream().collect(Collectors.toMap(Dish::isVegetarian, Dish::getCalories, Integer::sum));
    }

    public Map<Boolean, Integer> getDishCalorieInfoByToMapWithBinaryOperatorAndSupplier(List<Dish> dishes, Supplier<Map<Boolean, Integer>> supplier) {
        return dishes.stream().collect(Collectors.toMap(Dish::isVegetarian, Dish::getCalories, Integer::sum, supplier));
    }

}
