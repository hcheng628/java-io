package us.hcheng.javaio.learnhspedu.chapters.qq.client.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import us.hcheng.javaio.learnhspedu.chapters.qq.common.entity.Msg;
import us.hcheng.javaio.learnhspedu.chapters.qq.common.util.SocketUtil;

public class ClientMsgService {
	public static void showOnlineFriends() {
		sendMsg(SocketService.getSocket(), new Msg(Msg.MsgType.ONLINE_FRIENDS));
	}

	public static void sendMessageToOne(String content, String from, String to) {
		Msg msg = new Msg();
		msg.setContent(content);
		msg.setFrom(from);
		msg.setTo(to);
		msg.setType(Msg.MsgType.MSG);
		sendMsg(SocketService.getSocket(), msg);
	}

	public static void sendFileToOne(String path, String from, String to) {
		Msg msg = new Msg();
		msg.setFrom(from);
		msg.setTo(to);
		msg.setType(Msg.MsgType.FILE);
		File f = new File(path);
		if (f.exists()) {
			try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f))) {
				msg.setData(bis.readAllBytes());
				msg.setContent(f.getName());
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		sendMsg(SocketService.getSocket(), msg);
	}

	public static void sendMessageToAll(String content, String from) {
		Msg msg = new Msg();
		msg.setContent(content);
		msg.setFrom(from);
		msg.setType(Msg.MsgType.MSG_ALL);
		sendMsg(SocketService.getSocket(), msg);
	}

	public static void sendMsg(Socket socket, Serializable msg) {
		SocketUtil.sendMsg(socket, msg);
	}

	public static Object getMsg(Socket socket) {
		return SocketUtil.getMsg(socket);
	}

}
