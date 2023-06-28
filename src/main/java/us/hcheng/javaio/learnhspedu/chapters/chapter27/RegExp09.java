package us.hcheng.javaio.learnhspedu.chapters.chapter27;

import java.util.Map;

/**
 * 非贪婪匹配
 */
public class RegExp09 {

    public static void main(String[] args) {
        String txt = "hello111111 ok";

        Map.of("默认", "\\d+",
                "不贪婪", "\\d+?").forEach((k, v) -> {
            System.out.println("Start ===" + k + "=== Start");
            Util.processMatching(Util.getMatcher(txt, v));
            System.out.println("End ===" + k + "=== End");
        });
    }

}
