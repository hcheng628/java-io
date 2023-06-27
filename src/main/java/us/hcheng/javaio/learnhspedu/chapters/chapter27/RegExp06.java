package us.hcheng.javaio.learnhspedu.chapters.chapter27;

/**
 * 定位符的使用
 */
public class RegExp06 {

    public static void main(String[] args) {
        //part1();
        part2();
    }

    private static void part1() {
        String txt = "123-abc";
        //以至少1个数字开头 后接任意个小写字母的字符串
        Util.processMatching(Util.getMatcher(txt, "^[\\d]+[a-z]*"));
        //以至少1个数字开头 必须以至少一个小写字母结束
        Util.processMatching(Util.getMatcher(txt, "^[\\d]+-[a-z]+$"));
    }

    /**
     * 表示匹配边界的han
     * 这里的边界是: 被匹配的字符串最后 也可以是空格的子字符串的后面
     */
    private static void part2() {
        String txt = "hanshunping sphan nnhan hanx xhanx han";
        Util.processMatching(Util.getMatcher(txt, "han\\b"));
        System.out.println("****************");
        Util.processMatching(Util.getMatcher(txt, "han\\B"));
        System.out.println("****************");
        Util.processMatching(Util.getMatcher(txt, "^han\\B"));
    }

}
