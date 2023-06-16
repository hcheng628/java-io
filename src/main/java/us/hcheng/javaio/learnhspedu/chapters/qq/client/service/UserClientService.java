package us.hcheng.javaio.learnhspedu.chapters.qq.client.service;

import java.net.Socket;
import us.hcheng.javaio.learnhspedu.chapters.qq.common.entity.Msg;
import us.hcheng.javaio.learnhspedu.chapters.qq.common.entity.User;
import us.hcheng.javaio.learnhspedu.chapters.qq.common.util.SocketUtil;

public class UserClientService {

	public boolean login(User user) {
		Socket socket = SocketService.getSocket();
		SocketUtil.sendMsg(socket, user);
		Object o = SocketUtil.getMsg(socket);
		if (!(o instanceof Msg))
			return false;
		return ((Msg) o).getType() == Msg.MsgType.LOGIN;
	}

	public void logout(User user) {
		Socket socket = SocketService.getSocket();
		Msg msg = new Msg();
		msg.setType(Msg.MsgType.LOGOUT);
		msg.setFrom(user.getUsername());
		SocketUtil.sendMsg(socket, msg);
	}

}
