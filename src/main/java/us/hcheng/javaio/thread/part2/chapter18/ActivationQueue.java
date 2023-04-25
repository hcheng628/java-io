package us.hcheng.javaio.thread.part2.chapter18;

import java.util.LinkedList;
import us.hcheng.javaio.thread.part2.chapter18.requests.MethodRequest;

public class ActivationQueue {

	private final LinkedList<MethodRequest> fRequests;
	private static final int MAX_METHOD_REQUEST  = 100;

	public ActivationQueue() {
		this.fRequests = new LinkedList<>();
	}

	public void putRequest(MethodRequest methodRequest) {
		try {
			synchronized (fRequests) {
				while (fRequests.size() >= MAX_METHOD_REQUEST)
					fRequests.wait();
				fRequests.addLast(methodRequest);
				fRequests.notifyAll();
			}
		} catch (InterruptedException ex) {
			System.out.println("ActivationQueue putRequest ex: " + ex);
		}
	}

	public MethodRequest takeRequest() {
		MethodRequest ret = null;
		try {
			synchronized (fRequests) {
				while (fRequests.isEmpty())
					fRequests.wait();
				ret = fRequests.removeFirst();
				fRequests.notifyAll();
			}
		} catch (InterruptedException ex) {
			System.out.println("ActivationQueue putRequest ex: " + ex);
		}

		return ret;
	}

}
