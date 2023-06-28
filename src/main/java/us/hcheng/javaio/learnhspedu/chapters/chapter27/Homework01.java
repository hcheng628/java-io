package us.hcheng.javaio.learnhspedu.chapters.chapter27;

import java.util.stream.Stream;

/**
 * 规定电子邮件规则为
 * 只能有一个@
 * @前面是用户名可以是 a-z A-Z 0-9 _- 字符
 * @后面是域名 并且域名只能是英文字母 比如 sohu.com 或者 tsinghua.org.cn
 */
public class Homework01 {

	public static void main(String[] args) {
		Stream.of("hsp@tsinghua.org.cn kkk",
				"dasf@dslafjhlsaa.csdadsa",
				"moka@gatech.edu")
				.forEach(e -> System.out.println("匹配***" + e + "*** " +(check(e) ? "成功" : "失败")));
	}

	private static boolean check(String email) {
		return Util.getMatcher(email, "^[a-zA-Z0-9_-]+@([a-zA-Z]+[.])+[a-zA-Z]+$").matches();
	}

}
