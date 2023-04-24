package us.hcheng.javaio.thread.part2.chapter18.entity;

public class FutureResult extends Result {

	private Result result;
	private boolean ready;

	public synchronized void setResult(Result result) {
		this.result = result;
		this.ready = true;
		this.notifyAll();
	}

	@Override
	public synchronized Object getResultValue() {
		try {
			while (!this.ready)
				this.wait();
		} catch (InterruptedException e){
			throw new RuntimeException(e);
		}

		return result.getResultValue();
	}

}
