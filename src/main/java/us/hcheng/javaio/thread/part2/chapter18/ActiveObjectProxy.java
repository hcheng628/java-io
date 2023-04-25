package us.hcheng.javaio.thread.part2.chapter18;

import us.hcheng.javaio.thread.part2.chapter18.entity.FutureResult;
import us.hcheng.javaio.thread.part2.chapter18.entity.Result;
import us.hcheng.javaio.thread.part2.chapter18.requests.DisplayStringRequest;
import us.hcheng.javaio.thread.part2.chapter18.requests.MakeStringRequest;

public class ActiveObjectProxy implements ActiveObject {

	private final Servant fServant;
	private final SchedulerThread fScheduler;

	public ActiveObjectProxy(Servant servant, SchedulerThread scheduler) {
		fServant = servant;
		fScheduler = scheduler;
	}

	@Override
	public Result makeString(char c, int len) {
		FutureResult futureResult = new FutureResult();
		fScheduler.invoke(new MakeStringRequest(fServant, futureResult, c, len));
		return futureResult;
	}

	@Override
	public void displayString(String s) {
		fScheduler.invoke(new DisplayStringRequest(fServant, s));
	}

}
