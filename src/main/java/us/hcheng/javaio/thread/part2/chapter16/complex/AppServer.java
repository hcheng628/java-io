package us.hcheng.javaio.thread.part2.chapter16.complex;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AppServer extends Thread {

    private static final int DEFAULT_PORT = 12866;
    private final int port;
    private final List<ClientHandler> handlers;
    private final ServerSocket server;
    private volatile boolean terminated;
    private final ExecutorService executorService;

    public AppServer() {
        this(DEFAULT_PORT);
    }

    public AppServer(int port) {
        this.port = port;
        this.handlers = new ArrayList<>();

        new Thread(()->{
            while (!terminated) {
                try {
                    TimeUnit.SECONDS.sleep(7);
                    System.out.println(handlers);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "Server Status Thread").start();

        this.executorService = Executors.newFixedThreadPool(10);
        try {
            this.server = new ServerSocket(this.port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            while (!terminated) {
                ClientHandler handler = new ClientHandler(this.server.accept());
                handlers.add(handler);
                executorService.submit(handler);
            }
        } catch (IOException e) {
            System.err.println("AppServer IOException terminated... " + e);
        } finally {
            dispose();
        }
    }

    private void dispose() {
        System.out.println("Server Stopped by " + Thread.currentThread());
        executorService.shutdownNow();
        handlers.forEach(ClientHandler::stop);
    }

    public void shutdown() throws IOException {
        if (terminated)
            return;
        terminated = true;
        //this.interrupt();
        this.server.close();
    }

}
