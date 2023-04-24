package us.hcheng.javaio.thread.part2.chapter16.complex;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private volatile boolean terminated;
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter printer = new PrintWriter(socket.getOutputStream())) {
            while (!terminated) {
                final String msg = br.readLine();
                if (msg == null)
                    break;
                System.out.println(String.join(":", "Client said", msg));
                printer.write(String.join(":", "Server Moka", msg.toUpperCase()));
                printer.flush();
            }
        } catch (IOException e) {
            System.out.println("Client Handler Exception: " + e);
        } finally {
            stop();
        }
    }

    public void stop() {
        if (terminated)
            return;

        System.out.println("Client Stopped by " + Thread.currentThread());
        terminated = true;
        if (socket != null)
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Client socket close failed: " + e);
            }
    }

}
