package us.hcheng.javaio.learnhspedu.chapters.chapter21.socket;

import us.hcheng.javaio.learnhspedu.chapters.chapter21.util.IOUtils;
import us.hcheng.javaio.learnhspedu.chapters.chapter21.util.PlayWav;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;
import static us.hcheng.javaio.learnhspedu.chapters.chapter21.Constants.TCP_SERVER_PORT;

public class TCPServer2 {

	public static void main(String[] args) {
//		doTCPClient(TCP_SERVER_PORT, TCPServer2::doReaderAndWriter);
//		doTCPClient(TCP_SERVER_PORT, TCPServer2::doStream);
		doTCPClient(TCP_SERVER_PORT, TCPServer2::processFile);
	}

	private static void doTCPClient(int port, Consumer<Socket> consumer) {
		System.out.println("TCP Server is listening to " + port);
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
			System.err.println("Server Received: " + br.readLine());
			IOUtils.pushBWString(bw, "Hello from Server!");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private static void doStream(Socket socket) {
		try (BufferedInputStream is = new BufferedInputStream(socket.getInputStream());
			 BufferedOutputStream os = new BufferedOutputStream(socket.getOutputStream())) {
			System.out.print("Server Received: " + IOUtils.inputStreamToString(is));
			IOUtils.pushStreamString(socket, os, "你好呀 from Server");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private static void processFile(Socket socket) {
		try (BufferedInputStream is = new BufferedInputStream(socket.getInputStream())) {
			byte[] data = is.readAllBytes();
			new PlayWav(new ByteArrayInputStream(data)).start();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
