package us.hcheng.javaio.java8.entity.spliterator;

import java.util.Spliterator;
import java.util.function.Consumer;
import us.hcheng.javaio.java8.util.Utils;

public class MySpliterator implements Spliterator<String> {

	private final boolean DEBUG = false;
	private final int LIMIT = 5;
	private final String[] data;
	private int start;
	private int end;

	public MySpliterator(int start, int end, String[] data) {
		this.start = start;
		this.end = end;
		this.data = data;
	}

	@Override
	public boolean tryAdvance(Consumer<? super String> action) {
		print("tryAdvance...");

		if (this.start < this.end) {
			action.accept(data[start++]);
			return true;
		}

		return false;
	}

	@Override
	public Spliterator<String> trySplit() {
		print("trySplit...");
		int mid = (end - start) / 2 + start;
		if (mid - start <= LIMIT)
			return null;

		Spliterator<String> ret = new MySpliterator(start, mid, data);
		start = mid + 1;
		return ret;
	}

	@Override
	public long estimateSize() {
		print("estimateSize...");
		return this.end - this.start;
	}

	@Override
	public int characteristics() {
		print("characteristics...");
		return CONCURRENT | Spliterator.SIZED;
	}

	private void print(String msg) {
		if (DEBUG)
			Utils.print(msg);
	}

}
