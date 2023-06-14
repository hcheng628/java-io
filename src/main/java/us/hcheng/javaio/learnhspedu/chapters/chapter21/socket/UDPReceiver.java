package us.hcheng.javaio.learnhspedu.chapters.chapter21.socket;

import static us.hcheng.javaio.learnhspedu.chapters.chapter21.Constants.BASE_PATH;
import static us.hcheng.javaio.learnhspedu.chapters.chapter21.Constants.UDP_SERVER_PORT;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import us.hcheng.javaio.learnhspedu.chapters.chapter21.util.PlayWav;

public class UDPReceiver {

	public static void main(String[] args) throws Exception {
		receiveText();
		receiveFile();
	}

	public static void receiveText() {
		byte[] buffer = new byte[512];
		try (DatagramSocket datagramSocket = new DatagramSocket(UDP_SERVER_PORT)) {
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			System.out.println("UDP Receiver waiting...");
			datagramSocket.receive(packet);
			System.out.println("UDP Receiver got " + new String(packet.getData(), 0, packet.getLength()));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void receiveFile() {
		byte[] buffer = new byte[4096 * 10];
		try (DatagramSocket datagramSocket = new DatagramSocket(UDP_SERVER_PORT)) {
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			// hard code 4 just to receive 4 packets or the full file, can be done programmatically
			try (FileOutputStream fileOutputStream = new FileOutputStream(BASE_PATH + "test.wav")) {
				for (int i = 0; i < 4; i++) {
					datagramSocket.receive(packet);
					fileOutputStream.write(packet.getData(), 0, packet.getLength());
				}
				new PlayWav(BASE_PATH + "test.wav").start();
			} catch (IOException ex){}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
