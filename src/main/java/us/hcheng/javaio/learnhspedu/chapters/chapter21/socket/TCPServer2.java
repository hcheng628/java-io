package us.hcheng.javaio.learnhspedu.chapters.chapter21.socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class TCPServer2 {

	public static void main(String[] args) {
		final int port = 8866;
		//doTCPClient(port, TCPServer2::doReaderAndWriter);
		doTCPClient(port, TCPServer2::doStream);
	}

	private static void doTCPClient(int port, Consumer<Socket> consumer) {
		System.out.println(TCPServer2.class.getSimpleName() + " listening " + port);
		try (ServerSocket server = new ServerSocket(port);
		     Socket socket = server.accept()) {
			consumer.accept(socket);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private static void doReaderAndWriter(Socket socket) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		     BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
			System.out.println("Server Received: " + br.readLine());

			bw.write("Hello from Server!");
			bw.newLine();
			bw.flush();
			/**
			 * It needs to flush() and new line if the other end readLine()
			 */
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private static void doStream(Socket socket) {
		try (BufferedInputStream is = new BufferedInputStream(socket.getInputStream());
		     BufferedOutputStream os = new BufferedOutputStream(socket.getOutputStream())) {
			byte[] buffer = new byte[1024];
			StringBuilder sb = new StringBuilder();
			for (int len = -1; (len = is.read(buffer)) != -1; )
				sb.append(new String(buffer, 0, len));
			System.out.print("Server Received: " + sb);

			os.write("你好呀 from Server".getBytes());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
