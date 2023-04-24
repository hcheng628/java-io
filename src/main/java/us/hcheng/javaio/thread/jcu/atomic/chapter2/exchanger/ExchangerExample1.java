package us.hcheng.javaio.thread.jcu.atomic.chapter2.exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

public class ExchangerExample1 {

	public static void main(String[] args) {
		Exchanger<String> exchanger = new Exchanger<>();
		runTask("T1", 1, exchanger);
		runTask("T2", 3, exchanger);
		// runTask("T3", 1, exchanger); Exchanger is only meant to be used by 2 Threads
	}

	private static void runTask(String name, int second, Exchanger<String> exchanger) {
		new Thread(()-> {
			try {
				System.out.println(name + " Ready");
				TimeUnit.SECONDS.sleep(second);
				String receivedData = exchanger.exchange("Hey there from " + name);
				System.out.println(name + " received: " + receivedData);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}, name).start();
	}

}
