package us.hcheng.javaio.learnhspedu.chapters.chapter27;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Matcher 类的常用方法
 */
public class Matcher101 {
    private static final String TXT = "hello edu jack hspedutom hello smith hello hspedu hspedu";

    public static void main(String[] args) {
        part1();
        part2();
    }

    private static void part1() {
        String regStr = "hello";

        Matcher matcher = Pattern.compile(regStr).matcher(TXT);
        for (int g = 0; matcher.find(); g++) {
            int start = matcher.start();
            int end = matcher.end();
            System.out.println("G " + g + ": " + TXT.substring(start, end) + " [" + start + ", " + end + "]");
        }

        System.out.println("整体匹配: " + matcher.matches());
        System.out.println("整体匹配: " + Pattern.compile("hello").matcher(regStr).matches());
    }

    /**
     * 如果有 hspedu 替换成 韩顺平教育
     */
    private static void part2() {
        String regStr = "hspedu";
        String res = Pattern.compile(regStr).matcher(TXT).replaceAll("韩顺平教育");
        System.out.println(TXT);
        System.out.println(res);
    }

}
