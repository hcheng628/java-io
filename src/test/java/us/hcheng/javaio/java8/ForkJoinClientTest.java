package us.hcheng.javaio.java8;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.function.Function;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import us.hcheng.javaio.java8.features.ForkJoinClient;

class ForkJoinClientTest {

	// 10000
	private static final int SIZE = 10000;
	private static ForkJoinClient client;
	private static int[] data;
	private static long answer;

	@BeforeAll
	static void init() {
		client = new ForkJoinClient();
		data = new int[SIZE];
		IntStream.rangeClosed(1, SIZE).forEach(i -> {
			data[i - 1] = i;
			answer += i;
		});
	}

	@Test
	void sumByRecursiveTaskTest() {
		long res = simluate(client::sumByRecursiveTask, data, "sumByRecursiveTask");
		assertEquals(answer, res);
	}

	@Test
	void sumByRecursiveActionTest() {
		long res = simluate(client::sumByRecursiveAction, data, "sumByRecursiveAction");
		assertEquals(answer, res);
	}


	private Long simluate(Function<int[], Long> fun, int[] arr, String name) {
		long duration = -1;
		long res = -1;

		for (int i = 0; i < 10; i++) {
			long start = System.currentTimeMillis();
			res = fun.apply(arr);
			long curr = System.currentTimeMillis() - start;
			if (duration == -1 || curr < duration)
				duration = curr;
		}

		System.out.println(name + " time spent: " + duration);
		return res;
	}

}
