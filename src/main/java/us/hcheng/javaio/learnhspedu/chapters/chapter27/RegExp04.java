package us.hcheng.javaio.learnhspedu.chapters.chapter27;

import java.util.regex.Pattern;

/**
 * 选择匹配符
 */
public class RegExp04 {

    public static void main(String[] args) {
        String content = "hanshunping 韩 寒冷";
        Util.processMatching(Pattern.compile("han|韩|寒").matcher(content));
    }

}
