package us.hcheng.javaio.learnhspedu.chapters.chapter27;

import java.util.regex.Matcher;
import java.util.stream.Stream;

/**
 * 用正则表达式匹配 URL 并得到 协议 域名 端口 资源名
 */
public class Homework03 {

	public static void main(String[] args) {
		Stream.of("http://www.sohu.com:8080/abc/xxx/yyy/inde@#$%x.htm",
				"http://www.sohu.com:8080/abc/xxx/yyy/////inde@#$%x.htm",
				"http://www.sohu.com:8080/",
				"http://www.sohu.com:8080")
				.forEach(Homework03::check);
	}

	private static void check(String url) {
		String validChars = "a-zA-Z0-9~!@#$%^&*.()_+";
		String regExp = "^(?<protocol>[a-zA-Z]+):[/]{2}(?<domain>[\\w-]+([.]\\w+)+):(?<port>\\d{1,5})/*(?<dontcare>[" + validChars + "]+/)*(?<res>[" + validChars + "]+)*$";
		Matcher matcher = Util.getMatcher(url, regExp);

		if (matcher.find()) {
			StringBuilder sb = new StringBuilder("Valid URL: " + url).append(System.lineSeparator());
			Stream.of("protocol", "domain", "port", "dontcare", "res").forEach(k ->
					sb.append(k + ": " + matcher.group(k)).append(System.lineSeparator())
			);
			System.out.println(sb);
		} else
			System.err.println("Invalid URL: " + url);
	}

}
