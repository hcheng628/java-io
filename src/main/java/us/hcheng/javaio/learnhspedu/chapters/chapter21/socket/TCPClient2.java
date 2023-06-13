package us.hcheng.javaio.learnhspedu.chapters.chapter21.socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.function.Consumer;

public class TCPClient2 {

	public static void main(String[] args) {
		final int port = 8866;
		//doTCPClient(port,TCPClient2::doReaderAndWriter);
		doTCPClient(port,TCPClient2::doStream);
	}

	private static void doTCPClient(int port, Consumer<Socket> consumer) {
		System.out.println(TCPServer2.class.getSimpleName() + " connecting at " + port);
		try (Socket socket = new Socket(InetAddress.getLocalHost(), port)) {
			consumer.accept(socket);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private static void doReaderAndWriter(Socket socket) {
		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		     BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
			bw.write("Hello from Client!" + System.lineSeparator());
			//bw.newLine();
			bw.flush();
			/**
			 * It needs to flush() and new line if the other end readLine()
			 */
			System.out.println("Client Received: " + br.readLine());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private static void doStream(Socket socket) {
		try (BufferedInputStream is = new BufferedInputStream(socket.getInputStream());
		     BufferedOutputStream os = new BufferedOutputStream(socket.getOutputStream())) {
			socket.getOutputStream().write("你好呀 from Client".getBytes());

			byte[] buffer = new byte[1024];
			StringBuilder sb = new StringBuilder();
			for (int len = -1; (len = is.read(buffer)) != -1; )
				sb.append(new String(buffer, 0, len));

			System.out.print("Client Received: " + sb);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
