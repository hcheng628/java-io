package us.hcheng.javaio.learnhspedu.chapters.qq.server.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import us.hcheng.javaio.learnhspedu.chapters.qq.common.entity.Msg;
import us.hcheng.javaio.learnhspedu.chapters.qq.common.util.Utility;

@Data
@AllArgsConstructor
public class BroadcastService implements Runnable {
	boolean running;
	public BroadcastService() {
		this(true);
	}
	@Override
	public void run() {
		while(running) {
			System.err.print("\n请输入在线用户广播信息: ");
			Msg msg = new Msg(Utility.readString(50), Msg.MsgType.BROADCAST);
			SocketMgtService.onlineUsers().forEach(u -> ServerMsgService.sendMsg(SocketMgtService.getSocketByUser(u), msg));
		}
	}

}
