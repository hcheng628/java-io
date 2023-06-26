package us.hcheng.javaio.learnhspedu.chapters.projects.qq.client.service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import us.hcheng.javaio.learnhspedu.chapters.projects.qq.common.entity.Constants;

public class SocketService {
	private static Socket socket;

	public static void init() {
		try {
			socket = new Socket(InetAddress.getLocalHost(), Constants.TCP_PORT);
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
