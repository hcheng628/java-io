package us.hcheng.javaio.learnhspedu.chapters.chapter21.socket;

import us.hcheng.javaio.learnhspedu.chapters.chapter21.util.IOUtils;
import us.hcheng.javaio.learnhspedu.chapters.chapter21.util.PlayWav;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.function.Consumer;
import static us.hcheng.javaio.learnhspedu.chapters.chapter21.Constants.BASE_PATH;
import static us.hcheng.javaio.learnhspedu.chapters.chapter21.Constants.TCP_SERVER_PORT;

public class TCPClient {

	public static void main(String[] args) {
		doTCPClient(TCP_SERVER_PORT, TCPClient::doReaderAndWriter);
		doTCPClient(TCP_SERVER_PORT, TCPClient::doStream);
		doTCPClient(TCP_SERVER_PORT, TCPClient:: sendFile);
		doTCPClient(TCP_SERVER_PORT, TCPClient:: streamPlay);
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
		try (BufferedOutputStream os = new BufferedOutputStream(socket.getOutputStream());
			InputStream is = socket.getInputStream()) {
			IOUtils.pushByteData(socket, os, IOUtils.inputStreamToByteArr(new FileInputStream(BASE_PATH + "music.wav")));
			System.out.println("File upload: " + ("success".equals(IOUtils.inputStreamToString(is)) ? " Yes" : " No"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private static void streamPlay(Socket socket) {
		try (Scanner scanner = new Scanner(System.in);
		BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
		BufferedInputStream bis = new BufferedInputStream(socket.getInputStream())) {
			System.out.println("Enter Name: ");
			String name = scanner.nextLine();
			IOUtils.pushStreamString(socket, bos, name);

			byte[] data = IOUtils.inputStreamToByteArr(bis);
			if (data != null)
				new PlayWav(new ByteArrayInputStream(data)).start();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
