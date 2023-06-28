package us.hcheng.javaio.learnhspedu.chapters.chapter27;

import java.util.Arrays;

/**
 * 反向引用
 */
public class RegExp12 {
    public static void main(String[] args) {
        part1();
        part2();
    }

    /**
     * 要匹配两个连续的相同数字
     * 要匹配五个连续的相同数字
     * 要匹配个位与千位相同, 十位与百位相同的数5225, 1551
     */
    private static void part1() {
        final String txt = "h1234el9876lo33333 j12324-333999111a1551ck14 tom11 jack22 yyy12345 xxx";
        String[] regExps = {"(\\d)\\1", "(\\d)\\1{4}", "(\\d)(\\d)\\2\\1"};
        Arrays.stream(regExps).forEach(r -> Util.processMatching(Util.getMatcher(txt, r)));
    }

    /**
     * 请在字符串中检索商品编号 形式如:12321-333999111 这样的号码
     * 要求: 前面是一个五位数 然后一个- 然后一个九位数(连续的每三位要相同)
     */
    private static void part2() {
        String txt = "12321-333999111";
        String regExp = "\\d{5}-(\\d)\\1{2}(\\d)\\2{2}(\\d)\\3{2}";
        Util.processMatching(Util.getMatcher(txt, regExp));
    }
}
