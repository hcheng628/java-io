package us.hcheng.javaio.learnhspedu.chapters.chapter27;

import static us.hcheng.javaio.learnhspedu.chapters.chapter27.Constants.HEADLINES_TXT;
import static us.hcheng.javaio.learnhspedu.chapters.chapter27.Constants.IP_ADDR_TXT;
import static us.hcheng.javaio.learnhspedu.chapters.chapter27.Constants.JAVA_TXT;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class RegExp01 {

	public static void main(String[] args) {
		basics();
		getHeadlines();
		getIPs();
	}

	/**
	 *  假定编写了爬虫 从百度页面得到如下文本
	 *      提取文章中所有的英文单词
	 * 		提取文章中所有的数字
	 * 		提取文章中所有的英文单词和数字
	 * 		    (1). 传统方法. 使用遍历方式, 代码量大, 效率不高
	 * 		    (2). 正则表达式技术
	 */
	private static void basics() {
		String regExpS1 = "[a-zA-Z]+";
		String regExpS2 = "[0-9]+";
		String regExpS3 = "([0-9]+)|([a-zA-Z]+)";
		//1. 先创建一个Pattern对象 ， 模式对象, 可以理解成就是一个正则表达式对象
		Stream.of(regExpS1, regExpS2, regExpS3).forEach(regExpS -> {
			//2. 创建一个匹配器对象
			//理解: 就是 matcher 匹配器按照 pattern(模式/样式), 到 content 文本中去匹配
			Pattern pattern = Pattern.compile(regExpS);
			Matcher matcher = pattern.matcher(JAVA_TXT);
			//3. processMatching里 matcher 开始循环匹配
			Util.processMatching(matcher);
		});
	}

	private static void getIPs() {
		String regExStr1 = "(\\d+)\\.(\\d+)\\.(\\d+)\\.(\\d+)";  // play with groups (4 + 1 groups)
		String regExStr2 = "\\d+\\.\\d+\\.\\d+\\.\\d+";          // 1 group
		Stream.of(regExStr1, regExStr2).forEach(regStr -> {
			Pattern pattern = Pattern.compile(regExStr2);
			Matcher matcher = pattern.matcher(IP_ADDR_TXT);
			Util.processMatching(matcher);
		});
	}

	private static void getHeadlines() {
		String regExStr = "<a target=\"_blank\" title=\"(\\S+)\"";  // use group to better access later
		Pattern pattern = Pattern.compile(regExStr);
		Matcher matcher = pattern.matcher(HEADLINES_TXT);
		Util.processMatching(matcher);
	}



}
