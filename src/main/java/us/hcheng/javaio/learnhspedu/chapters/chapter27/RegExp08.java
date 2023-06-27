package us.hcheng.javaio.learnhspedu.chapters.chapter27;

/**
 * 非捕获分组
 */
public class RegExp08 {
    private static final String TXT = "hello韩顺平教育 jack韩顺平老师 韩顺平同学hello韩顺平学生";

    public static void main(String[] args) {
        part1();
        part2();
        part3();
    }

    /**
     * 找到 "韩顺平教育", "韩顺平老师", "韩顺平同学" 子字符串
     */
    private static void part1() {
        Util.processMatching(Util.getMatcher(TXT, "韩顺平(?:教育|老师|同学)"));
        Util.processMatching(Util.getMatcher(TXT, "韩顺平教育|韩顺平老师|韩顺平同学"));
    }

    /**
     * 找到 "韩顺平" 这个关键字要求只是查找 韩顺平教育 和 韩顺平老师 中包含有的韩顺平
     */
    private static void part2() {
        Util.processMatching(Util.getMatcher(TXT, "韩顺平(?=教育|老师)"));   //韩顺平    韩顺平
        Util.processMatching(Util.getMatcher(TXT, "韩顺平教育|韩顺平老师"));  //韩顺平教育 韩顺平老师
    }

    /**
     * 找到 "韩顺平" 这个关键字, 但是要求只是查找 不是 (韩顺平教育 和 韩顺平老师) 中包含有的韩顺平
     */
    private static void part3() {
        Util.processMatching(Util.getMatcher(TXT, "韩顺平(?!教育|老师)"));
    }

}
