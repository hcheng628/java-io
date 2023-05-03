package us.hcheng.javaio.java8.entity;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class ToListCollector<T> implements Collector<T, List<T>, List<T>> {

	private final boolean DEBUG = false;

	@Override
	public Supplier<List<T>> supplier() {
		print("supplier...");
		return LinkedList::new;
	}

	@Override
	public BiConsumer<List<T>, T> accumulator() {
		return (list, element) -> {
			print("accumulator..." + list + " adding " + element);
			list.add(element);
		};
	}

	@Override
	public BinaryOperator<List<T>> combiner() {
		return (l1, l2) -> {
			print("combiner... l1: " + l1 + " vs l2: " + l2);
			l1.addAll(l2);
			return l1;
		};
	}

	@Override
	public Function<List<T>, List<T>> finisher() {
		return ret -> {
			print("finisher... ret: ");
			for (T t : ret)
				print(t.toString() + " by finisher");
			return ret;
		};
	}

	@Override
	public Set<Characteristics> characteristics() {
		print("characteristics...");
		return Set.of(Characteristics.UNORDERED);
	}

	private void print(String msg) {
		if (!DEBUG)
			return;

		StringBuilder sb = new StringBuilder("Thread-");
		sb.append(Thread.currentThread().getName()).append(":").append(msg);
		sb.append("\nAt:").append(System.currentTimeMillis()).append("\n");
		System.out.println(sb);
	}

}
