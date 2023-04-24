package us.hcheng.javaio.thread.part2.chapter9;

import us.hcheng.javaio.thread.part2.chapter9.entity.Request;
import java.util.LinkedList;

/**
 * Guarded Suspension
 */
public class RequestQueue <T> {

    private final LinkedList<Request<T>> requests;

    public RequestQueue() {
        requests = new LinkedList<>();
    }

    public Request<T> getRequest() {
        synchronized (requests) {
            try {
                while (requests.isEmpty())
                    requests.wait();
                return requests.removeFirst();
            } catch (InterruptedException ex) {
                System.out.println("Return null at getRequest - InterruptedException: " + ex);
                return null;
            }
        }
    }

    public void putRequest(Request<T> request) {
        synchronized(requests) {
            requests.addLast(request);
            requests.notifyAll();
        }
    }

}
