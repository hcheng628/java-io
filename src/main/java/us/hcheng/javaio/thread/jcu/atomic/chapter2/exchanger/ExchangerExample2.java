package us.hcheng.javaio.thread.jcu.atomic.chapter2.exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

public class ExchangerExample2 {

	public static void main(String[] args) {
		Exchanger<Person> exchanger = new Exchanger<>();
		runTask("T1", 1, exchanger);
		runTask("T2", 3, exchanger);
	}

	private static void runTask(String name, int second, Exchanger<Person> exchanger) {
		new Thread(()-> {
			try {
				System.out.println(name + " Ready");
				TimeUnit.SECONDS.sleep(second);
				boolean isT1 = name.contains("1");
				Person sendData = new Person(isT1 ? "Moka" : "Cheng");
				if (isT1) {
					new Thread(()-> {
						sendData.setName("MOKA");
					}, "MODIFY-NAME").start();
				}

				System.out.println(name + " about to send: " + sendData);
				Person receivedData = exchanger.exchange(sendData);
				System.out.println(name + " received: " + receivedData + " " + receivedData.getName());
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}, name).start();
	}

	static class Person {
		String name;

		public Person(String name) {
			this.name = name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}
	}
}
