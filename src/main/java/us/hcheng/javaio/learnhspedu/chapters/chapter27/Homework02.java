package us.hcheng.javaio.learnhspedu.chapters.chapter27;

import java.util.stream.Stream;

/**
 *  要求验证是不是整数或者小数
 *  提示： 这个题要考虑正数和负数
 *  比如： 123 -345 34.89 -87.9 -0.01 0.45...
 */
public class Homework02 {

	public static void main(String[] args) {
		Stream.of("123", "-345", "34.89",
				"0.01", "+0.01", "fda.01",
				"-87.9", "-0.01", "0.45",
				"000.15", "-0.89").forEach(Homework02::check);
	}

	private static void check(String num) {
		String regExp = "^([+|-])?(0[1-9]*|[1-9]\\d*)([.]\\d+)*";
		if (Util.getMatcher(num, regExp).matches())
			System.out.println("Pass: " + num);
		else
			System.err.println("Fail: " + num);
	}

}
