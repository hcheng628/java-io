package us.hcheng.javaio.learnhspedu.chapters.chapter19.file;

import org.junit.Test;
import java.io.File;
import java.io.IOException;

/**
 * when creating a file, the parent directory path has to be present
 */
public class CreateFile {

    /**
     * path has to be a valid path, it cannot create directories
     */
    @Test
    public void create01() {
        String path = "/Users/nikkima/Desktop/a.txt";
        File file = new File(path);
        try {
            file.createNewFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Parent Directory has to be present
     */
    @Test
    public void create02() {
        String dirPath = "/Users/nikkima/Desktop/";
        File dirFile = new File(dirPath);
        try {
            new File(dirFile, "b.txt").createNewFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Parent Directory has to be present
     */
    @Test
    public void create03() {
        String dirPath = "/Users/nikkima/Desktop";
        try {
            new File(dirPath, "b.txt").createNewFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
