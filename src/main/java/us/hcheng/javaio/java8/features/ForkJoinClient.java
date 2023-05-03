package us.hcheng.javaio.java8.features;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;

import us.hcheng.javaio.java8.entity.forkjoin.SumRecursiveAction;
import us.hcheng.javaio.java8.entity.forkjoin.SumRecursiveTask;

public class ForkJoinClient {


	/**
	 * use get() or use join() with catch statement
	 */
	public Long sumByRecursiveTask(int[] arr) {
		SumRecursiveTask task = new SumRecursiveTask(0, arr.length - 1, arr);
		new ForkJoinPool().submit(task);
		return task.join();
	}

	public Long sumByRecursiveAction(int[] arr) {
		SumRecursiveAction action = new SumRecursiveAction(0, arr.length - 1, arr);
		new ForkJoinPool().submit(action);
		action.join();
		return action.getResult();
	}

}
