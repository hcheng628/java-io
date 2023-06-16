package us.hcheng.javaio.learnhspedu.chapters.qq.server.service;

import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SocketMgtService {

	public static ConcurrentHashMap<String, ServerClientMgtService> map = new ConcurrentHashMap<>();

	public static void addUser(String username, ServerClientMgtService clientMgtService) {
		map.put(username, clientMgtService);
	}

	public static void removeUser(String username) {
		map.remove(username);
	}

	public static Socket getSocketByUser(String username) {
		ServerClientMgtService mgtService = map.get(username);
		return mgtService == null ? null : mgtService.getSocket();
	}

	public static Set<String> onlineUsers() {
		return map.keySet();
	}

}
