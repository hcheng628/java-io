package us.hcheng.javaio.thread.part2.chapter13.service;

import us.hcheng.javaio.thread.part2.chapter13.entity.Job;

public interface IMessageBus<T> {

    Job<T> take() throws InterruptedException;
    void put(Job<T> job) throws InterruptedException ;

}
