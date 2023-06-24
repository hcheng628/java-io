package us.hcheng.javaio.learnhspedu.chapters.chapter21.socket;

import static us.hcheng.javaio.learnhspedu.chapters.chapter21.Constants.BASE_PATH;
import static us.hcheng.javaio.learnhspedu.chapters.chapter21.Constants.UDP_SERVER_PORT;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPSender {

	public static void main(String[] args) {
		sendText();
		sendFile();
	}

	private static void sendText() {
		byte[] data = "Hello from UDP Sender".getBytes();
		try (DatagramSocket socket = new DatagramSocket()) {
			DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName("127.0.0.1"), UDP_SERVER_PORT);
			socket.send(packet);
			System.out.println("The packets are sent successfully");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void sendFile() {
		try (DatagramSocket socket = new DatagramSocket()) {
			FileInputStream fis = new FileInputStream(BASE_PATH + "tank-war/music.wav");
			byte[] buffer = new byte[4096 * 10];
			for (int len = -1; (len = fis.read(buffer)) != -1; )
				socket.send(new DatagramPacket(buffer, 0, len, InetAddress.getByName("127.0.0.1"), UDP_SERVER_PORT));
			System.out.println("The packets are sent successfully");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
