package us.hcheng.javaio.java8.entity.forkjoin;

import java.util.concurrent.RecursiveTask;

public class SumRecursiveTask extends RecursiveTask<Long> {

	private final int left, right;
	private final int[] data;

	public SumRecursiveTask(int left, int right, int[] data) {
		this.left = left;
		this.right = right;
		this.data = data;
	}

	@Override
	protected Long compute() {
		if (right - left < 6) {
			long ret = 0;

			for (int i = 0, end = right - left; i <= end; i++)
				ret += data[i + left];

			return ret;
		} else {
			int mid = (right - left) / 2 + left;
			SumRecursiveTask leftTask = new SumRecursiveTask(left, mid, data);
			SumRecursiveTask rightTask = new SumRecursiveTask(mid + 1, right, data);
			leftTask.fork();
			return leftTask.join() + rightTask.compute();
		}
	}

}
