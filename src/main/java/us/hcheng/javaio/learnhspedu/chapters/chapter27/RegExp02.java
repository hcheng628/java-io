package us.hcheng.javaio.learnhspedu.chapters.chapter27;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class RegExp02 {

	/**
	 *  匹配 ( or ) => \\(
	 *  匹配 . => \\.
	 *  匹配 d => \\d
	 *  匹配 $ => \\$
	 * @param args
	 */
	public static void main(String[] args) {
		String content = "aabc$(a.bc(123( )";

		Stream.of("c$", "c\\$", "c\\(1",
				"a.b", "a\\.b", "ddd",
				"\\d\\d\\d", "\\)").forEach(regEx -> {
			System.out.println("Matching==========" + regEx + "==========");
			Pattern pattern = Pattern.compile(regEx);
			Matcher matcher = pattern.matcher(content);
			while (matcher.find()) {
				for (int i = 0; i <= matcher.groupCount(); i++)
					System.out.println("group#" + i + " " + matcher.group(i));
				System.out.println();
			}
		});

	}



}
