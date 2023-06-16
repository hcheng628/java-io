package us.hcheng.javaio.learnhspedu.chapters.qq.server.service;

import java.io.IOException;
import java.net.Socket;
import lombok.AllArgsConstructor;
import lombok.Data;
import us.hcheng.javaio.learnhspedu.chapters.qq.common.entity.Msg;
import us.hcheng.javaio.learnhspedu.chapters.qq.common.entity.User;

@Data
@AllArgsConstructor
public class ServerClientMgtService implements Runnable {
	private final User user;
	private final Socket socket;

	@Override
	public void run() {
		while (!socket.isClosed()) {
			Object o = ServerMsgService.getMsg(socket);
			if (!(o instanceof Msg))
				continue;

			Msg msg = (Msg) o;
			Msg.MsgType type = msg.getType();

			if (type == Msg.MsgType.LOGOUT) {
				System.out.println("EXIT Listening client socket... " + user);
				try {
					socket.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				break;
			} else if (type == Msg.MsgType.MSG || type == Msg.MsgType.FILE)
				ServerMsgService.sendMsg(SocketMgtService.getSocketByUser(msg.getTo()), msg);
			else if (type == Msg.MsgType.MSG_ALL) {
				SocketMgtService.onlineUsers().stream()
						.filter(k -> !k.equals(msg.getFrom()))
						.forEach(k -> ServerMsgService.sendMsg(SocketMgtService.getSocketByUser(k), msg));
			} else if (type == Msg.MsgType.ONLINE_FRIENDS) {
				StringBuilder sb = new StringBuilder();
				SocketMgtService.onlineUsers().forEach(k -> sb.append(k).append(' '));
				msg.setContent(sb.toString());
				ServerMsgService.sendMsg(socket, msg);
			}
		}
	}

}
