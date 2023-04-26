package us.hcheng.javaio.thread.jcu.collections;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class SimpleSkipListApp {

	/* */
	public static void main(String[] args) {
		SimpleSkipList skipList = new SimpleSkipList();
		Set<Integer> addedNumbers = new HashSet<>();
		List<Integer> nonExistNumbers = new ArrayList<>();

		IntStream.range(0, 50).forEach(i -> addedNumbers.add(ThreadLocalRandom.current().nextInt(200)));
		while (nonExistNumbers.size() < 5) {
			int num = ThreadLocalRandom.current().nextInt(200);
			if (!addedNumbers.contains(num))
				nonExistNumbers.add(num);
		}

		addedNumbers.forEach(i -> skipList.add(i));
		addedNumbers.forEach(i -> {
			if (!skipList.contains(i))
				System.err.println("PART 1: contains issue");
		});

		nonExistNumbers.forEach(i -> {
			if (skipList.contains(i))
				System.err.println("PART 2: contains issue");
		});

		skipList.dumpList();
	}

}
