package us.hcheng.javaio.thread.part2.chapter18.requests;

import us.hcheng.javaio.thread.part2.chapter18.Servant;
import us.hcheng.javaio.thread.part2.chapter18.entity.FutureResult;

public abstract class MethodRequest {

	protected Servant fServant;
	protected FutureResult fFutureResult;

	protected MethodRequest(Servant fServant, FutureResult fFutureResult) {
		this.fServant = fServant;
		this.fFutureResult = fFutureResult;
	}

	public abstract void execute();

}
