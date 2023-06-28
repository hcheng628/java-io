package us.hcheng.javaio.learnhspedu.chapters.chapter27;

import java.util.regex.Pattern;

/**
 * 用于整体匹配 在验证输入的字符串是否满足条件使用
 */
public class Pattern101 {

    public static void main(String[] args) {
        String txt = "hello abc hello, 韩顺平教育";
        System.out.println(Pattern.compile("^hello").matcher(txt).matches());
        System.out.println(Pattern.compile("^hello(.)*").matcher(txt).matches());
    }

}
