package us.hcheng.javaio.thread.part2.chapter18.requests;

import com.cheng.c18.Servant;
import com.cheng.c18.entity.FutureResult;

public abstract class MethodRequest {

	protected Servant fServant;
	protected FutureResult fFutureResult;

	protected MethodRequest(Servant fServant, FutureResult fFutureResult) {
		this.fServant = fServant;
		this.fFutureResult = fFutureResult;
	}

	public abstract void execute();
}
