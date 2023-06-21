package us.hcheng.javaio.learnhspedu.chapters.qq.server.service;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import us.hcheng.javaio.learnhspedu.chapters.qq.common.entity.Msg;

@Data
@AllArgsConstructor
public class OfflineMsgService {

	private static ConcurrentHashMap<String, List<Msg>> map = new ConcurrentHashMap<>();

	public static void addOfflineMsg(Msg msg) {
		List<Msg> msgs = map.computeIfAbsent(msg.getTo(), k -> new ArrayList<>());
		msgs.add(msg);
	}

	public static void removeOfflineMsg(String username) {
		map.remove(username);
	}

	public static boolean hasOfflineMsg(String username) {
		return map.containsKey(username);
	}


	public static void process(String username) {
		if (!hasOfflineMsg(username))
			return;

		Socket socket = SocketMgtService.getSocketByUser(username);
		new Thread(() -> map.get(username).forEach(m -> {
			ServerMsgService.sendMsg(socket, m);
			OfflineMsgService.removeOfflineMsg(username);
		})).start();
	}

}
