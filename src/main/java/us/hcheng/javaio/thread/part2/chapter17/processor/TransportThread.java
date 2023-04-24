package us.hcheng.javaio.thread.part2.chapter17.processor;

import us.hcheng.javaio.thread.part2.chapter17.Channel;
import us.hcheng.javaio.thread.part2.chapter17.Request;
import us.hcheng.javaio.thread.part2.chapter17.util.SleepUtil;

public class TransportThread extends Thread {

    private final String name;
    private final Channel channel;

    public TransportThread(String name, Channel channel) {
        this.name = name;
        this.channel = channel;
    }

    @Override
    public void run() {
        for (int i = 0; i < Integer.MAX_VALUE && !channel.isTerminated(); i++) {
            this.channel.put(new Request(i, String.join("'", name, " request")));
            try {
                SleepUtil.sleep();
            } catch (RuntimeException ex) {
                System.out.println("TransportThread WAIT: " + ex);
            }
        }
    }
}
