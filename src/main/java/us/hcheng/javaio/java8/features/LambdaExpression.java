package us.hcheng.javaio.java8.features;

import us.hcheng.javaio.java8.entity.Apple;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class LambdaExpression {

    public static void main(String[] args) {
        /**
         *  parameters  ->     lambda body
         */
        Comparator<Apple> comparator = (a, b) -> a.getColor().compareTo(b.getColor());
        Function<Apple, String> appleColor = a -> a.getColor();
        Predicate<Apple> isGreen = a -> "green".equals(a.getColor());
        Consumer<Apple> apple = System.out::println;
        Supplier<Apple> supplier = () -> new Apple("blue", 168);
    }

}
