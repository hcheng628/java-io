package us.hcheng.javaio.thread.part2.chapter18.requests;

import com.cheng.c18.Servant;
import com.cheng.c18.entity.FutureResult;
import com.cheng.c18.entity.Result;

public class MakeStringRequest extends MethodRequest {

	private final char fChar;
	private final int fLen;

	public MakeStringRequest(Servant fServant, FutureResult fFutureResult, char c, int len) {
		super(fServant, fFutureResult);
		this.fChar = c;
		this.fLen = len;
	}

	@Override
	public void execute() {
		Result res = fServant.makeString(fChar, fLen);
		fFutureResult.setResult(res);
	}

}
