package us.hcheng.javaio.learnhspedu.chapters.chapter21.socket;

import us.hcheng.javaio.learnhspedu.chapters.chapter21.util.IOUtils;
import us.hcheng.javaio.learnhspedu.chapters.chapter21.util.PlayWav;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;
import static us.hcheng.javaio.learnhspedu.chapters.chapter21.Constants.BASE_PATH;
import static us.hcheng.javaio.learnhspedu.chapters.chapter21.Constants.TCP_SERVER_PORT;

public class TCPServer {

	public static void main(String[] args) {
		doTCPClient(TCP_SERVER_PORT, TCPServer::doReaderAndWriter);
		doTCPClient(TCP_SERVER_PORT, TCPServer::doStream);
		doTCPClient(TCP_SERVER_PORT, TCPServer::processFile);
		doTCPClient(TCP_SERVER_PORT, TCPServer::sendStreamMedia);
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
		try (BufferedInputStream is = new BufferedInputStream(socket.getInputStream());
			OutputStream os = socket.getOutputStream()) {
			byte[] data = IOUtils.inputStreamToByteArr(is);

			IOUtils.makeFile(data, BASE_PATH + "music.wav");

			new PlayWav(new ByteArrayInputStream(data)).start();
			IOUtils.pushStreamString(socket, os, "success");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private static void sendStreamMedia(Socket socket) {
		try (BufferedInputStream is = new BufferedInputStream(socket.getInputStream());
		     OutputStream os = socket.getOutputStream()) {
			String name = "game".equals(IOUtils.inputStreamToString(is)) ? "game_on.wav" : "music.wav";
			IOUtils.pushByteData(socket, os, IOUtils.inputStreamToByteArr(new FileInputStream(BASE_PATH + name)));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
