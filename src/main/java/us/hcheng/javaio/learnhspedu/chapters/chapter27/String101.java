package us.hcheng.javaio.learnhspedu.chapters.chapter27;

import java.util.Arrays;

public class String101 {

    public static void main(String[] args) {
        //part1();
        //part2();
        part3();
    }

    /**
     * 使用正则表达式方式，将 JDK1.3 和 JDK1.4 替换成JDK
     */
    private static void part1() {
        String content = "2000年5月，JDK1.3、JDK1.4和J2SE1.3相继发布，几周后其" +
                "获得了Apple公司Mac OS X的工业标准的支持。2001年9月24日，J2EE1.3发" +
                "布。" +
                "2002年2月26日，J2SE1.4发布。自此Java的计算能力有了大幅提升";

        String res1 = content.replaceAll("JDK1\\.(?:3|4)", "JDK");
        String res2 = content.replaceAll("(JDK1\\.3|JDK1[.]4)", "JDK");
        System.out.println(res1.equals(res2));
    }

    /**
     * 验证一个手机号 要求必须是以138 139 开头的
     */
    private static void part2() {
        String content = "13888889999";
        String regExp1 = "^13(?:8|9)\\d*$";
        String regExp2 = "^(138|139)(\\d*)$";

        boolean res1 = Util.getMatcher(content, regExp1).matches();
        boolean res2 = Util.getMatcher(content, regExp2).matches();
        System.out.println(res1 == res2);

        Util.processMatching(Util.getMatcher(content, regExp1));
        Util.processMatching(Util.getMatcher(content, regExp2));
    }

    /**
     * 按照 [#, -, ~, 数字] 来分割
     */
    private static void part3() {
        String content = "hello#abc-jack12smith~北京";
        Arrays.stream(content.split("(#|-|~|\\d+)")).forEach(System.out::println);
    }
}
