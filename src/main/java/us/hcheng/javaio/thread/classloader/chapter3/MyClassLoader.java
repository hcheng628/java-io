package us.hcheng.javaio.thread.classloader.chapter3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MyClassLoader extends ClassLoader {

    private String fileDir;
    private static final String PACKAGE = "us.hcheng.javaio.thread.classloader.chapter2";

    public MyClassLoader(String name, String fileDir) {
        super(name, null);
        this.fileDir = fileDir;
    }

    public MyClassLoader(String name, String fileDir, ClassLoader parent) {
        super(name, parent);
        this.fileDir = fileDir;
    }


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classFile = this.loadClassFromFile(name);
        return super.defineClass(String.join(".", PACKAGE, name), classFile, 0, classFile.length);
    }

    private byte[] loadClassFromFile(String name) {
        try{
            return Files.readAllBytes(Paths.get(fileDir.concat(name).concat(".class")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
