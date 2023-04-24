package us.hcheng.javaio.thread.part2.chapter18;

public class ActiveObjectFactory {

	private ActiveObjectFactory(){}

	public static ActiveObject getActiveObject() {
		Servant servant = new Servant();
		ActivationQueue queue = new ActivationQueue();
		SchedulerThread scheduler = new SchedulerThread(queue);
		ActiveObjectProxy ret = new ActiveObjectProxy(servant, scheduler);
		scheduler.start();

		return ret;
	}

}
