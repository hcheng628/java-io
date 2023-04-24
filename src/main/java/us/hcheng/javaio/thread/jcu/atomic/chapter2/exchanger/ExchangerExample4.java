package us.hcheng.javaio.thread.jcu.atomic.chapter2.exchanger;

import java.util.Random;
import java.util.concurrent.Exchanger;

public class ExchangerExample4 {

	public static void main(String[] args) {
		Exchanger<Integer> exchanger = new Exchanger<>();

		String name1 = "T1";
		String name2 = "T2";

		new Thread(()-> {
			try {
				Integer sendData = new Random().nextInt(100);
				System.out.println(name1 + " sent: " + sendData);
				exchanger.exchange(sendData);

				sendData = new Random().nextInt(100);
				System.out.println(name1 + " sent: " + sendData);
				exchanger.exchange(sendData);

				sendData = new Random().nextInt(100);
				Integer receivedData = exchanger.exchange(sendData);
				System.out.println(name1 + " received: " + receivedData);
			} catch (Exception e) {
				System.err.println(name1 + " " + e);
			}
		}, name1).start();

		new Thread(()-> {
			try {
				Integer sendData = new Random().nextInt(100);
				System.out.println(name2 + " sent: " + sendData);
				Integer receivedData = exchanger.exchange(sendData);
				System.out.println(name2 + " received: " + receivedData);
			} catch (Exception e) {
				System.err.println(name2 + " " + e);
			}
		}, name2).start();
	}

}
