package us.hcheng.javaio.learnhspedu.chapters.chapter21.socket;

import us.hcheng.javaio.learnhspedu.chapters.chapter21.util.IOUtils;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.function.Consumer;
import static us.hcheng.javaio.learnhspedu.chapters.chapter21.Constants.TCP_SERVER_PORT;

public class TCPClient2 {

	public static void main(String[] args) {
//		doTCPClient(TCP_SERVER_PORT,TCPClient2::doReaderAndWriter);
//		doTCPClient(TCP_SERVER_PORT,TCPClient2::doStream);
		doTCPClient(TCP_SERVER_PORT, TCPClient2:: sendFile);
	}

	private static void doTCPClient(int port, Consumer<Socket> consumer) {
		System.out.println("TCP Client is connecting to " + port);
		try (Socket socket = new Socket(InetAddress.getLocalHost(), port)) {
			consumer.accept(socket);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private static void doReaderAndWriter(Socket socket) {
		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			 BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
			IOUtils.pushBWString(bw, "Hello from Client!");
			System.err.println("Client Received: " + br.readLine());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private static void doStream(Socket socket) {
		try (BufferedInputStream is = new BufferedInputStream(socket.getInputStream());
			 BufferedOutputStream os = new BufferedOutputStream(socket.getOutputStream())) {
			IOUtils.pushStreamString(socket, os, "你好呀 from Client");
			System.out.print("Client Received: " + IOUtils.inputStreamToString(is));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private static void sendFile(Socket socket) {
		String path = "/Users/nikkima/hc-development/hc-repo/git/java-io/src/main/resources/game_on.wav";
		try (BufferedOutputStream os = new BufferedOutputStream(socket.getOutputStream())) {
			byte[] data = new FileInputStream(path).readAllBytes();
			os.write(data);
			os.flush();
			System.out.println("File sent");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
