package us.hcheng.javaio.learnhspedu.chapters.chapter27;

import java.util.regex.Matcher;

public class Util {

	public static void processMatching(Matcher matcher) {
		for (int count = 1; matcher.find(); count++) {
			//匹配内容, 文本, 放到 m.group(0) ...
			for (int i = 0; i <= matcher.groupCount(); i++)
				System.out.println("找到: " + count + " group#" + i + ": " + matcher.group(i));
			System.out.println();
		}
	}
}
