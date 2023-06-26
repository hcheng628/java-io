package us.hcheng.javaio.learnhspedu.chapters.chapter27;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegExp03 {
	private static final String CONTENT = "a11c8abc _ABCy @";
	private static final Map<String, String> MAP = new HashMap<>();

	public static void main(String[] args) {
		/* */
		MAP.put("匹配 a-z之间任意一个字符", "[a-z]");
		//MAP.put("匹配 z-a之间任意一个字符", "[z-a]");   PatternSyntaxException
		MAP.put("匹配 a-c之间任意一个字符", "[a-c]");
		MAP.put("匹配 A-Z之间任意一个字符", "[A-Z]");
		MAP.put("匹配 B-D之间任意一个字符", "[B-D]");

		MAP.put("匹配 abc 字符串[默认区分大小写]", "abc");
		MAP.put("匹配 abc 字符串[不区分大小写] 1", "(?i)abc");
		MAP.put("匹配 abc 字符串[不区分大小写] 2", "(?i)ABC");

		MAP.put("匹配 0-9 之间任意一个字符", "[0-9]");
		MAP.put("匹配 2-6 之间任意一个字符", "[2-6]");
		//MAP.put("匹配 6-2 之间任意一个字符", "[6-2]");  PatternSyntaxException

		MAP.put("匹配 不在 a-z之间任意一个字符", "[^a-z]");
		MAP.put("匹配 不在 b-f之间任意一个字符", "[^b-f]");

		MAP.put("匹配 不在 0-9之间任意一个字符", "[^0-9]");
		MAP.put("匹配 不在 3-6之间任意一个字符", "[^3-6]");

		MAP.put("匹配 在 abcd中任意一个字符 1", "[abcd]");
		MAP.put("匹配 在 abcd中任意一个字符 2", "[a-d]");

		MAP.put("匹配 不在 0-9的任意一个字符 1", "[^0-9]");
		MAP.put("匹配 不在 0-9的任意一个字符 2", "[^0-9]");
		MAP.put("匹配 不在 0-9的任意一个字符 3", "\\D");
		MAP.put("匹配 不在 0-9的任意一个字符 4", "[^\\d]");

		MAP.put("匹配 大小写英文字母, 数字，下划线 1", "\\w");
		MAP.put("匹配 大小写英文字母, 数字，下划线 2", "[\\w]");
		MAP.put("匹配 大小写英文字母, 数字，下划线 3", "[a-zA-Z0-9_]");
		MAP.put("匹配 大小写英文字母, 数字，下划线 4", "(?i)[a-z0-9_]");

		MAP.put("不匹配 大小写英文字母, 数字，下划线 1", "\\W");
		MAP.put("不匹配 大小写英文字母, 数字，下划线 2", "(?i)[^a-z0-9_]");

		MAP.put("匹配任何空白字符(空格,制表符等)", "\\s");
		MAP.put("匹配任何非空白字符 ,和\\s刚好相反", "[\\S]");
		MAP.put("匹配出 \\n(newline) 之外的所有字符,如果要匹配.本身则需要使用 \\.", ".");

		process();
	}

	private static void process() {
		for (Map.Entry<String, String> e : MAP.entrySet()) {
			System.out.println("******" + e.getKey() + "******Start******");
			Util.processMatching(Pattern.compile(e.getValue()).matcher(CONTENT));
			//Util.processMatching(Pattern.compile(e.getValue(), Pattern.CASE_INSENSITIVE).matcher(CONTENT));
			System.out.println("******" + e.getKey() + "******End******");
		}
	}

}
