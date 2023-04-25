package us.hcheng.javaio.thread.part2.chapter18.requests;

import us.hcheng.javaio.thread.part2.chapter18.Servant;

public class DisplayStringRequest extends MethodRequest {
	private final String fString;

	public DisplayStringRequest(Servant fServant, String string) {
		super(fServant, null);
		fString = string;
	}

	@Override
	public void execute() {
		this.fServant.displayString(fString);
	}

}
