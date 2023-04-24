package us.hcheng.javaio.thread.part2.chapter1;

import java.util.stream.IntStream;

public class Service {

	private static Service fService = new Service();

	public Service getInstance() {
		return fService;
	}

	public void process(int i) throws InterruptedException {
		if (i % 3 == 0) {
			Thread.sleep(2_000);
			System.out.println("I will sleep: " + i);
		} else {
			System.out.println("I will not sleep");
		}
	}

	public static void main(String[] args) {
		Service service = new Service().getInstance();


		IntStream.range(0, 34).forEach(i -> {
			new Thread(()->{
				try {
					service.process(i);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}, "t" + i).start();
		});
	}




}
