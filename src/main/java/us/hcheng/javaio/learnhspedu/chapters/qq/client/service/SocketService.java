package us.hcheng.javaio.learnhspedu.chapters.qq.client.service;

import static us.hcheng.javaio.learnhspedu.chapters.qq.common.entity.Constants.TCP_PORT;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import us.hcheng.javaio.learnhspedu.chapters.qq.common.util.SocketUtil;

public class SocketService {
	private static Socket socket;

	public static void init() {
		try {
			socket = new Socket(InetAddress.getLocalHost(), TCP_PORT);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void close() {
		try {
			socket.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static Socket getSocket() {
		return socket;
	}

}
