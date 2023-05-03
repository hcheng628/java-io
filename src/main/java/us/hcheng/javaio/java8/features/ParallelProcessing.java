package us.hcheng.javaio.java8.features;

import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ParallelProcessing {

	public static void main(String[] args) {
		final int limit = 100_000_000;

		measureSumPerformance(ParallelProcessing::normalAdd, limit, "normalAdd");

		measureSumPerformance(ParallelProcessing::iterateStream, limit, "iterateStream");
		measureSumPerformance(ParallelProcessing::iterateStreamP, limit, "iterateStreamP");

		measureSumPerformance(ParallelProcessing::longStream1, limit, "longStream1");
		measureSumPerformance(ParallelProcessing::longStream1P, limit, "longStream1P");

		measureSumPerformance(ParallelProcessing::longStream2, limit, "longStream2");
		measureSumPerformance(ParallelProcessing::longStream2P, limit, "longStream2P");
	}


	private static long measureSumPerformance(Function<Integer, Long> function, int limit, String name) {
		long duration = -1;
		long res = -1;

		for (int i = 0; i < 10; i++) {
			long start = System.currentTimeMillis();
			res = function.apply(limit);
			long curr = System.currentTimeMillis() - start;
			if (duration == -1 || curr < duration)
				duration = curr;
		}

		System.out.println(new StringBuilder("res: ").append(res).append(" \ttook:").append(duration).append("  \t by").append(name));
		return duration;
	}

	private static Long normalAdd(int limit) {
		long ret = 0L;
		for (int i = 1; i <= limit; i++)
			ret += i;
		return ret;
	}

	private static Long iterateStream(int limit) {
		return Stream.iterate(1L, ret -> ret + 1).limit(limit).reduce(0L, Long::sum);
	}

	private static Long iterateStreamP(int limit) {
		return Stream.iterate(1L, ret -> ret + 1).limit(limit).parallel().reduce(0L, Long::sum);
	}

	private static Long longStream1(int limit) {
		return LongStream.rangeClosed(1, limit).reduce(0L, Long::sum);
	}

	private static Long longStream1P(int limit) {
		return LongStream.rangeClosed(1, limit).parallel().reduce(0L, Long::sum);
	}

	private static Long longStream2(int limit) {
		return LongStream.rangeClosed(1, limit).sum();
	}

	private static Long longStream2P(int limit) {
		return LongStream.rangeClosed(1, limit).parallel().sum();
	}

}
