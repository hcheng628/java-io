package us.hcheng.javaio.thread.part2.chapter12;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class BalkingData {
    private final String filename;
    private String content;
    private boolean changed;

    public BalkingData(String filename, String content) {
        this.filename = filename;
        this.content = content;
        this.changed = true;
    }

    /**
     * Users update
     */
    public synchronized void update(String content) {
        System.out.println("update from [" + this.content + "] to [" + content + "] by " + Thread.currentThread().getName());
        this.content = content;
        changed = true;
    }

    /**
     * Service manages requests(update)
     */
    public synchronized void save() {
        if (!changed) {
            System.out.println(Thread.currentThread().getName() + " has nothing to do...");
            return;
        }
        doSave();
        changed = false;
    }

    private void doSave() {
        System.out.println("processing ... " + content + " by " + Thread.currentThread().getName());
        /*
        try (Writer writer = new FileWriter(filename, true)) {
            writer.write(content);
            writer.write(System.lineSeparator());
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        */
    }

}
