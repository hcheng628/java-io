package us.hcheng.javaio.thread.part2.chapter18;

import com.cheng.c18.entity.Result;

public interface ActiveObject {

	Result makeString(char c, int len);
	void displayString(String s);
}
