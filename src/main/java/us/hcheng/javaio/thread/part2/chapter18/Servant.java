package us.hcheng.javaio.thread.part2.chapter18;

import java.util.Arrays;
import us.hcheng.javaio.thread.part2.chapter18.entity.RealResult;
import us.hcheng.javaio.thread.part2.chapter18.entity.Result;

public class Servant implements ActiveObject {

	@Override
	public Result makeString(char c, int len) {
		//FutureResult futureResult = new FutureResult();
		char[] chars = new char[len];
		Arrays.fill(chars, c);
		return new RealResult(new String(chars));
	}

	@Override
	public void displayString(String s) {
		System.out.println(s);
	}

}
