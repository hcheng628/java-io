package us.hcheng.javaio.learnhspedu.chapters.chapter27;

import java.util.stream.Stream;

/**
 * 匹配URL思路
 *  1. 先确定 url 的开始部分 https:// | http://
 *  2.然后通过 ([\w-]+\.)+[\w-]+ 匹配 www.bilibili.com
 *  3. /video/BV1fh411y7R8?from=sear 匹配(\/[\w-?=&/%.#]*)?
 */
public class RegExp11 {

    public static void main(String[] args) {
        String t1 = "https://www.bilibili.com/video/咖啡BV1fh411y7R8?from=search&seid=1831060912083761326";
        String t2 = "http://edu.3dsmax.tech/yg/bilibili/my6652/pc/qg/05-51/index.html#201211-1?track_id=jMc0jn-hm-yHrNfVad37YdhOUh41XYmjlss9zocM26gspY5ArwWuxb4wYWpmh2Q7GzR7doU0wLkViEhUlO1qNtukyAgake2jG1bTd23lR57XzV83E9bAXWkStcAh4j9Dz7a87ThGlqgdCZ2zpQy33a0SVNMfmJLSNnDzJ71TU68Rc-3PKE7VA3kYzjk4RrKU";
        String t3 = "www.bilibili.com/video";
        String t4 = "www.cnbc.com";
        String t5 = "www.cn bc.com89123";
        String t6 = "www.cn bc_com89123fdsa摩卡";
        String t7 = "www.bili-bili.com.cn/video";
        String t8 = "www.bili-bili.com.cn.cnn/video/vvv";

        String regExp = "^(http(s)?://)?[\\w-]+[.][\\w-]+([.]\\w+)+(/\\S+)*";
        //"^((http|https)://)?([\\w-]+\\.)+[\\w-]+(\\/[\\w-?=&/%.#]*)?$";//注意：[. ? *]表示匹配就是.本身

        Stream.of(t1, t2, t3, t4, t5, t6, t7, t8).forEach(t -> {
            System.out.println("*************" + t + "*************");
            Util.processMatching(Util.getMatcher(t, regExp));
        });
    }

}
