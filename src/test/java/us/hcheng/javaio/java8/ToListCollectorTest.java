package us.hcheng.javaio.java8;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import us.hcheng.javaio.java8.entity.Dish;
import us.hcheng.javaio.java8.entity.ToListCollector;

class ToListCollectorTest {

	private static ToListCollector<Dish> collector;
	private List<Dish> dishes;

	@BeforeAll
	static void init() {
		collector = new ToListCollector<>();
	}

	@BeforeEach
	void setup() {
		dishes = new ArrayList<>();
		IntStream.range(0, 100).forEach(i -> Stream.of(
				new Dish("pork", false, 800, Dish.Type.MEAT),
				new Dish("beef", false, 700, Dish.Type.MEAT),
				new Dish("chicken", false, 400, Dish.Type.MEAT),
				new Dish("french fries", true, 530, Dish.Type.OTHER),
				new Dish("rice", true, 350, Dish.Type.OTHER),
				new Dish("season fruit", true, 120, Dish.Type.OTHER),
				new Dish("pizza", true, 550, Dish.Type.OTHER),
				new Dish("prawns", false, 300, Dish.Type.FISH),
				new Dish("salmon", false, 450, Dish.Type.FISH))
				.forEach(d -> dishes.add(d))
		);
	}

	@Test
	void ToListCollectorTest() {
		List<Dish> list = dishes.stream().parallel().collect(collector);
		assertEquals(900, list.size());
		assertEquals(LinkedList.class, list.getClass());
	}

}

