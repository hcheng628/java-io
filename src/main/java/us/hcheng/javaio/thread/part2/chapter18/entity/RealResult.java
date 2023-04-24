package us.hcheng.javaio.thread.part2.chapter18.entity;

public class RealResult extends Result {

	private final Object result;

	public RealResult(Object result) {
		this.result = result;
	}

	@Override
	public Object getResultValue() {
		return this.result;
	}
}
