package us.hcheng.javaio.learnhspedu.chapters.chapter27;

import java.util.regex.Pattern;

public class RegExp13 {

    public static void main(String[] args) {
        String txt = "我....我要....学学学学....编程java!";
        // 去掉所有的.
        txt = Pattern.compile("[.]").matcher(txt).replaceAll("");
        // (.)\\1+ 反向引用$1 来替换匹配到的内容
        txt = Pattern.compile("(.)\\1+").matcher(txt).replaceAll("$1");
        System.out.println(txt);
    }

}
