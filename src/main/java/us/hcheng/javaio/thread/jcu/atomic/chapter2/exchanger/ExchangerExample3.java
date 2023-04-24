package us.hcheng.javaio.thread.jcu.atomic.chapter2.exchanger;

import java.util.Random;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

public class ExchangerExample3 {

	public static void main(String[] args) {
		Exchanger<Integer> exchanger = new Exchanger<>();
		runTask("T1", 500, exchanger);  // always stop from T1 side
		runTask("T2", 500, exchanger);
	}

	private static void runTask(String name, int second, Exchanger<Integer> exchanger) {
		new Thread(()-> {
			try {
				System.out.println(name + " Ready");
				boolean isT1 = name.contains("1");

				while (true) {
					TimeUnit.MILLISECONDS.sleep(second);
					Integer sendData = new Random().nextInt(10);
					System.out.println(name + " sent: " + sendData);
					Integer receivedData = exchanger.exchange(sendData);
					System.out.println(name + " received: " + receivedData);

					if (isT1) {
						if (sendData == 1) {
							System.out.println("T1 Stopping and send 99 to T2");
							exchanger.exchange(99);
							break;
						}
					} else {
						if (receivedData == 99) {
							System.out.println("T2 Stopping also");
							break;
						}
					}
				}
			} catch (Exception e) {
				System.err.println(name + " " + e);
			}

			System.out.println(name + " Complete");
		}, name).start();
	}
}
