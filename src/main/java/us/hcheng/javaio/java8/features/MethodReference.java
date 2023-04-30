package us.hcheng.javaio.java8.features;

import us.hcheng.javaio.java8.entity.Apple;
import us.hcheng.javaio.java8.entity.SuperApple;
import us.hcheng.javaio.java8.entity.TriFunction;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class MethodReference {

    public static void main(String[] args) {
        Consumer<String> stringConsumer = System.out::println;
        stringConsumer.accept("Moka");

        List<Apple> list = Arrays.asList(new Apple("abc", 123),
                new Apple("Green", 110),
                new Apple("red", 123));

        list.sort(Comparator.comparing(Apple::getColor));
        System.out.println(list);
        list.sort((a, b) -> b.getColor().compareTo(a.getColor()));
        System.out.println(list);

        String strInt = "123";
        Function<String, Integer> parseInt = Integer::parseInt;
        System.out.println(parseInt.apply(strInt) == Integer.parseInt(strInt));

        Supplier<String> strSupplier = String::new;
        System.out.println(strSupplier.get());

        /**
         * smart order of BiFunction makes the code clean
         */
        String moka = "Moka";
        int mokaIdx = 3;
        BiFunction<String, Integer, Character> charAt1 = String::charAt;
        BiFunction<Integer, String, Character> charAt2 = (i, s) -> s.charAt(i);
        Function<Integer, Character> charAt3 = moka::charAt;

        System.out.println(charAt1.apply(moka, mokaIdx) == charAt2.apply(mokaIdx, moka) &&
                charAt2.apply(mokaIdx, moka) == charAt3.apply(mokaIdx));

        BiFunction<String, Long, Apple> createApple = Apple::new;
        System.out.println(createApple.apply("Blue", 123L));


        TriFunction<String, Long, String, SuperApple> createSuperApple = SuperApple::new;
        System.out.println(createSuperApple.apply("Super Dark", 666L, "HEBEI"));
    }

}
