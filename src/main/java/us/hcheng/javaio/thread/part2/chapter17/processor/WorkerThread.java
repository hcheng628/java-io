package us.hcheng.javaio.thread.part2.chapter17.processor;

import us.hcheng.javaio.thread.part2.chapter17.Channel;
import us.hcheng.javaio.thread.part2.chapter17.Request;
import us.hcheng.javaio.utils.SleepUtil;

public class WorkerThread extends Thread {

    private final String name;
    private final Channel channel;


    public WorkerThread(String name, Channel channel) {
        super(name);
        this.name = name;
        this.channel = channel;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Request request = this.channel.get();
                request.execute();
                SleepUtil.sleep();
            } catch (RuntimeException ex) {
                System.err.println(this.name + " off work!!!");
                break;
            }
        }
    }

}
