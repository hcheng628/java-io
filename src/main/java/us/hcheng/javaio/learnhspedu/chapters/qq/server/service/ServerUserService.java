package us.hcheng.javaio.learnhspedu.chapters.qq.server.service;

import java.net.Socket;
import java.util.Map;
import us.hcheng.javaio.learnhspedu.chapters.qq.common.entity.User;
import us.hcheng.javaio.learnhspedu.chapters.qq.common.util.SocketUtil;

public class ServerUserService {

	public User getUser(Socket socket) {
		Object o = SocketUtil.getMsg(socket);
		return !(o instanceof User) ? null : (User) o;
	}

	public boolean login(User user) {
		Map<String, User> m = Map.of(
				"1", new User("1", "123"),
				"2", new User("2", "123"),
				"3", new User("3", "123"),
				"至尊宝", new User("至尊宝", "123"),
				"紫霞仙子", new User("紫霞仙子", "123"),
				"菩提老祖", new User("菩提老祖", "123"));

		User dbUser = m.get(user.getUsername());
		return dbUser != null && dbUser.getPassword().equals(user.getPassword());
	}

}
