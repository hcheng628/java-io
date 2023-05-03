package us.hcheng.javaio.java8.entity.spliterator;

import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class MySpliteratorWrapper {

	private final String[] DATA;
	private final String DELIMITER = "\n";

	public MySpliteratorWrapper(String s) {
		Objects.requireNonNull("CANNOT BE NULL STRING");
		this.DATA = s.split(DELIMITER);
	}

	public Stream<String> stream(boolean parallel) {
		return StreamSupport.stream(new MySpliterator(0, DATA.length, DATA), parallel);
	}

	public Stream<String> stream() {
		return stream(false);
	}

}
