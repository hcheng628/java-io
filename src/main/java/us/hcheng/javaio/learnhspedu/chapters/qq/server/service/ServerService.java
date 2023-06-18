package us.hcheng.javaio.learnhspedu.chapters.qq.server.service;

import static us.hcheng.javaio.learnhspedu.chapters.qq.common.entity.Constants.TCP_PORT;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import us.hcheng.javaio.learnhspedu.chapters.qq.common.entity.Msg;
import us.hcheng.javaio.learnhspedu.chapters.qq.common.entity.User;

public class ServerService {
	private ServerUserService userService;
	private boolean exit = false;

	public ServerService() {
		userService = new ServerUserService();
	}

	public void start() {
		Socket socket = null;
		try (ServerSocket serverSocket = new ServerSocket(TCP_PORT)) {
			System.out.println(new StringBuilder("服务端在").append(TCP_PORT).append("端口监听..."));
			while (!exit) {
				socket = serverSocket.accept();//如果没有客户端连接，就会阻塞在这里
				User user = userService.getUser(socket);
				String username = user.getUsername();
				if (userService.login(user)) {
					ServerMsgService.sendMsg(socket, new Msg(Msg.MsgType.LOGIN));
					ServerClientMgtService mgtService = new ServerClientMgtService(user, socket);
					SocketMgtService.addUser(username, mgtService);
					new Thread(mgtService, String.join(":", "ChatServer", username)).start();
				} else {
					System.err.println("用户 id=" + username + " pwd=" + user.getPassword() + " 验证失败");
					ServerMsgService.sendMsg(socket, new Msg(Msg.MsgType.LOGIN_FAIL));
					socket.close();
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

}