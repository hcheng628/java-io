package us.hcheng.javaio.java8.features;

import java.util.Optional;
import java.util.stream.Stream;

import us.hcheng.javaio.java8.entity.spliterator.MySpliteratorWrapper;

public class SpliteratorClient {

	public Optional<Long> countLine(String s) {
		MySpliteratorWrapper wrapper = new MySpliteratorWrapper(s);
		return Optional.ofNullable(wrapper.stream().count());
	}

	public Optional<Long> countLineParallel(String s) {
		MySpliteratorWrapper wrapper = new MySpliteratorWrapper(s);
		return Optional.ofNullable(wrapper.stream(true).count());
	}

	public Stream<String> getTextStream(String s) {
		return new MySpliteratorWrapper(s).stream();
	}

}
