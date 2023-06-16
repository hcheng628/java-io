package us.hcheng.javaio.learnhspedu.chapters.qq.common.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class SocketUtil {

	public static void sendMsg(Socket socket, Serializable msg) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(msg);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static Object getMsg(Socket socket) {
		try {
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			return ois.readObject();
		} catch (ClassNotFoundException | IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
