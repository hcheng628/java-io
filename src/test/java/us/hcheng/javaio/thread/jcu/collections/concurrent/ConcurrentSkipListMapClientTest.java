package us.hcheng.javaio.thread.jcu.collections.concurrent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import us.hcheng.javaio.thread.jcu.concurrent.ConcurrentSkipListMapClient;

public class ConcurrentSkipListMapClientTest {

	private static ConcurrentSkipListMap<Integer, String> map;

	@BeforeAll
	static void init() {
		map = ConcurrentSkipListMapClient.getInstance();
	}

	@BeforeEach
	void setup() {
		map.putAll(Map.of(5, "Moka", 33, "Cheng", 61, "Jordan"));
	}

	@AfterEach
	void tearDown() {
		map.clear();
	}

	@Test
	void ceilingsTest() {
		assertEquals(3, map.size());

		assertEquals("Cheng", map.ceilingEntry(33).getValue());
		assertEquals("Cheng", map.ceilingEntry(20).getValue());

		assertEquals(5, map.ceilingKey(-1));
		assertEquals(5, map.ceilingKey(5));

		assertNull(map.ceilingKey(100));
	}

	@Test
	void floorsTest() {
		assertEquals(3, map.size());

		assertEquals("Jordan", map.floorEntry(100).getValue());
		assertEquals("Jordan", map.floorEntry(61).getValue());

		assertEquals(61, map.floorKey(61));
		assertEquals(61, map.floorKey(100));

		assertNull(map.floorKey(4));
	}

	@Test
	void descendingsTest() {
		SortedSet<Integer> set = map.descendingKeySet();
		assertEquals(61, set.first());
		assertEquals(5, set.last());

		NavigableMap<Integer, String> m =  map.descendingMap();
		Map.Entry<Integer, String> e1 = m.firstEntry();
		Map.Entry<Integer, String> e2 = m.lastEntry();

		assertEquals("Jordan", e1.getValue());
		assertEquals(5, e2.getKey());
	}

	@Test
	void computeTest() {
		map.compute(5, (key, val) -> String.join(":", String.valueOf(key), val));

		assertEquals("5:Moka", map.get(5));

		assertThrows(NullPointerException.class, () -> {
			map.compute(null, (key, val) -> String.join(":", String.valueOf(key), val));
		});

		assertThrows(NullPointerException.class, () -> {
			map.compute(100, null);
		});

		int k = 23;
		map.compute(k, (key, val) -> String.join(":", String.valueOf(key), val));
		assertEquals("23:null", map.get(k));

		map.computeIfAbsent(5, i -> "---" + i + "---");
		assertEquals("5:Moka", map.get(5));

		String v = "---5---";
		map.computeIfPresent(5, (key, val) -> "---" + key + "---");
		assertEquals(v, map.get(5));
	}

	@Test
	void firstAndLastKeysTest() {
		assertEquals(5, map.firstKey());
		assertEquals(61, map.lastKey());
	}

	@Test
	void tailAndHeadMapsTest() {
		Map<Integer, String> m = map.tailMap(61, true);

		assertEquals(1, m.size());
		assertEquals("Jordan", map.get(61));

		assertTrue(map.headMap(5, false).isEmpty());
	}

}
