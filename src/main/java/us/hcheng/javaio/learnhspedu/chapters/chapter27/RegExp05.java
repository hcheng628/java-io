package us.hcheng.javaio.learnhspedu.chapters.chapter27;

/**
 * 限定符的使用
 * 细节: Java匹配默认贪婪匹配，即尽可能匹配多的
 */
public class RegExp05 {

    public static void main(String[] args) {
        String content = "a2111111a1a1a1a1aahello";
        Util.processMatching(Util.getMatcher(content, "a{3}"));
        Util.processMatching(Util.getMatcher(content, "\\d{2}"));
        Util.processMatching(Util.getMatcher(content, "a{3,4}"));
        //Util.processMatching(Util.getMatcher(content, "1{4,2}"));PatternSyntaxException
        Util.processMatching(Util.getMatcher(content, "\\d{2,}")); // 匹配两个以上的数字
        Util.processMatching(Util.getMatcher(content, "1+")); //匹配一个1或者多个1
        Util.processMatching(Util.getMatcher(content, "\\d+")); //匹配一个数字或者多个数字
        Util.processMatching(Util.getMatcher(content, "1*"));
        Util.processMatching(Util.getMatcher(content, "a1?")); ////匹配 a 或者 a1
    }

}
