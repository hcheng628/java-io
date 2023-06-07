package us.hcheng.javaio.learnhspedu.chapters.chapter16;

import java.util.stream.IntStream;
import us.hcheng.javaio.utils.SleepUtil;

public class Thread01 {

	public static void main(String[] args) {
		//bunThreadJoinTest();
		daemonThreadTest();
	}

	private static void daemonThreadTest() {
		Thread play = new Thread(new ParentsNotHome());
		play.setDaemon(true);
		play.start();

		for (int i = 0; i < 20; i++) {
			System.out.println("家长在工作 " + (i + 1));
			if (i == 5) {
				System.out.println("不行回家看看孩子有没有学习 " + (i + 1));
				break;
			}
			SleepUtil.sleepSec(1);
		}

		/**
		 * no print statement should be printed from child thread
		 * when main thread exits
		 */
		System.out.println("家长到家了 孩子没有打游戏 哈哈哈");
	}

	private static void bunThreadJoinTest() {
		Thread boss = new Thread(new BunThread());
		boss.start();

		IntStream.range(0, 20).forEach(i -> {
			System.out.println("小弟吃包子第 " + (i + 1));
			if (i == 5) {
				try {
					Thread.yield(); // this cannot be guaranteed it is up to OS
					boss.join();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			SleepUtil.sleepSec(1);
		});
	}

}

class BunThread implements Runnable {
	@Override
	public void run() {
		IntStream.range(0, 20).forEach(i -> {
			System.out.println("老大吃包子第 " + (i + 1));
			SleepUtil.sleepSec(1);
		});
	}
}

class ParentsNotHome implements Runnable {
	@Override
	public void run() {
		IntStream.range(0, 20).forEach(i -> {
			System.out.println("我们打游戏 " + (i + 1));
			SleepUtil.sleepSec(1);
		});
	}
}
