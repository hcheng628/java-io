package us.hcheng.javaio.learnhspedu.chapters.projects.qq.server.service;

import java.io.Serializable;
import java.net.Socket;
import us.hcheng.javaio.learnhspedu.chapters.projects.qq.common.util.SocketUtil;

public class ServerMsgService {

	public static void sendMsg(Socket socket, Serializable msg) {
		if (socket == null || socket.isClosed())
			return;
		SocketUtil.sendMsg(socket, msg);
	}

	public static Object getMsg(Socket socket) {
		return socket == null || socket.isClosed() ? null : SocketUtil.getMsg(socket);
	}

}
