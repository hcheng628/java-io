package us.hcheng.javaio.thread.part2.chapter18;

import com.cheng.c18.requests.MethodRequest;

public class SchedulerThread extends Thread {

	private final ActivationQueue fActivationQueue;

	public SchedulerThread(ActivationQueue fActivationQueue) {
		this.fActivationQueue = fActivationQueue;
	}

	public void invoke(MethodRequest request) {
		this.fActivationQueue.putRequest(request);
	}

	@Override
	public void run() {
		// Moka update
		while (true) {
			this.fActivationQueue.takeRequest().execute();
		}
	}

}
