package us.hcheng.javaio.java8.entity.forkjoin;

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicLong;

public class SumRecursiveAction extends RecursiveAction {

	private final AtomicLong result;
	private final int left, right;
	private final int[] data;

	public SumRecursiveAction(int left, int right, int[] data) {
		this.result = new AtomicLong();
		this.left = left;
		this.right = right;
		this.data = data;
	}

	@Override
	protected void compute() {
		long curr = 0;

		if (right - left < 6)
			for (int i = 0, end = right - left; i <= end; i++)
				curr += data[i + left];
		else {
			int mid = (right - left) / 2 + left;
			SumRecursiveAction leftTask = new SumRecursiveAction(left, mid, data);
			SumRecursiveAction rightTask = new SumRecursiveAction(mid + 1, right, data);
			leftTask.fork();
			leftTask.join();
			rightTask.compute();
			curr = leftTask.getResult() + rightTask.getResult();
		}

		result.getAndAdd(curr);
	}

	public Long getResult() {
		return result.get();
	}

}
