package us.hcheng.javaio.learnhspedu.chapters.chapter27;

/**
 * 非命分组 和 命名分组
 */
public class RegExp07 {
    private static final String TXT = "hanshunping s7789 nn1189han";

    /**
     *  说明
     *      1. matcher.group(0) 得到匹配到的字符串
     *      2. matcher.group(1) 得到匹配到的字符串的第1个分组内容
     *      3. matcher.group(2) 得到匹配到的字符串的第2个分组内容
     */
    public static void main(String[] args) {
        part1();
        part2();
    }

    private static void part1() {
        Util.processMatching(Util.getMatcher(TXT, "(\\d)(\\d{3})"));
    }

    /**
     * (?<name> xxx)
     */
    private static void part2() {
        Util.processMatching(Util.getMatcher(TXT, "(?<moka>\\d)(?<pot>\\d{3})"),
                "pot", "moka");
    }

}
