package us.hcheng.javaio.learnhspedu.chapters.chapter27;

import java.util.stream.Stream;

public class RegExp10 {

    public static void main(String[] args) {
        //part1();
        //part2();
        //part3();
        part4();
    }

    /**
     * 匹配汉字
     */
    private static void part1() {
        String txt = "i love moka 咖啡";
        Util.processMatching(Util.getMatcher(txt, "[\u2E80-\u2FD5\u3190-\u319f\u3400-\u4DBF\u4E00-\u9FCC\uF900-\uFAAD]+"));
    }


    /**
     * 邮政编码 要求：是1-9开头的一个六位数.  比如：123890
     */
    private static void part2() {
        String txt = "i love moka 咖啡0213218哈哈哈3980765";
        txt = "123890";
        Util.processMatching(Util.getMatcher(txt, "^[1-9]\\d{5}$"));
    }

    /**
     * QQ号码 要求: 1-9开头的一个(5位数-10位数)  比如: 12389, 1345687, 187698765
     */
    private static void part3() {
        String txt = "i love moka 咖啡0213218哈哈哈3980765";
        Stream.of("12389", "1345687", "187698765", txt)
                .forEach(each -> Util.processMatching(Util.getMatcher(each, "^[1-9]\\d{4,9}$")));
    }

    /**
     * 手机号码 要求: 必须以13, 14, 15, 18 开头的11位数 比如 13588889999
     */
    private static void part4() {
        Stream.of("12389", "1345687", "187698765", "18732128999")
                .forEach(each -> Stream.of("^(13|14|15|18)\\d{9}$", "^1(?:3|4|5|8)\\d{9}$")
                        .forEach(regEx -> Util.processMatching(Util.getMatcher(each, regEx))));

    }
}
