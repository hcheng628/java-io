package us.hcheng.javaio.java8;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Optional;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import us.hcheng.javaio.java8.features.SpliteratorClient;

class SpliteratorClientTest {

	private static final String DELIMITER = "\n";
	private static final int SIZE = 100;
	private static String text = null;
	private static SpliteratorClient client;

	@BeforeAll
	static void init() {
		StringBuilder sb = new StringBuilder();
		IntStream.rangeClosed(1, SIZE).forEach(i -> sb.append("LINELINELINE").append(i).append(DELIMITER));
		text = sb.toString();
		client = new SpliteratorClient();
	}

	@Test
	void countLineTest() {
		Optional<Long> res = client.countLine(text);
		assertTrue(res.isPresent());
		assertEquals(SIZE, res.get());

		res = client.countLine("");
		assertTrue(res.isPresent());
		assertEquals(1, res.get());

		assertThrows(NullPointerException.class, () -> client.countLine(null));
	}

	@Test
	void countLineParallelTest() {
		Optional<Long> res = client.countLineParallel(text);
		assertTrue(res.isPresent());
		assertEquals(SIZE, res.get());

		res = client.countLineParallel("");
		assertTrue(res.isPresent());
		assertEquals(1, res.get());

		assertThrows(NullPointerException.class, () -> client.countLineParallel(null));
	}

	@Test
	void getTextStreamTest() {
		long count = client.getTextStream(text).filter(s -> !s.isEmpty()).count();
		assertEquals(SIZE, count);
	}

}
