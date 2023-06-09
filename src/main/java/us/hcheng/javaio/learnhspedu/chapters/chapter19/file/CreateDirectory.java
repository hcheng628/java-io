package us.hcheng.javaio.learnhspedu.chapters.chapter19.file;

import org.junit.Test;
import java.io.File;

public class CreateDirectory {

    @Test
    public void m1() {
        File file = new File("/Users/nikkima/Desktop/a.txt");
        if (file.exists())
            System.out.println("文件删除" + (file.delete() ? "成功" : "失败"));
        else
            System.out.println("文件不存在");
    }

    @Test
    public void m2() {
        File file = new File("/Users/nikkima/Desktop/aaa");
        if (file.exists())
            System.out.println("目录删除" + (file.delete() ? "成功" : "失败"));
        else
            System.out.println("目录不存在");
    }

    /**
     * when necessary to create nested directories use mkdirs()
     * mkdir() needs the parent path is valid
     */
    @Test
    public void m3() {
        File dir = new File("/Users/nikkima/Desktop/aaa/bbb");
        if (dir.exists())
            System.out.println("目录存在");
        else
            System.out.println("创建目录" + (dir.mkdirs() ? "成功" : "失败"));
    }

    @Test
    public void m4() {
        File dir = new File("/Users/nikkima/Desktop/aaa");
        if (dir.exists()) {
            System.out.println(dir.delete());
        }
    }


}
