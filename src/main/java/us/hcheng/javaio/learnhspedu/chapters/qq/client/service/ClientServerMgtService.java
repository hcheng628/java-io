package us.hcheng.javaio.learnhspedu.chapters.qq.client.service;

import static us.hcheng.javaio.learnhspedu.chapters.qq.common.entity.Constants.BASE_FILE_DIR;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Data;
import us.hcheng.javaio.learnhspedu.chapters.qq.common.entity.Msg;
import us.hcheng.javaio.learnhspedu.chapters.qq.common.entity.User;

@Data
@AllArgsConstructor
public class ClientServerMgtService implements Runnable {
	private final User self;

	@Override
	public void run() {
		Socket socket = SocketService.getSocket();
		while (!socket.isClosed()) {
			Object o = ClientMsgService.getMsg(socket);
			if (!(o instanceof Msg))
				continue;

			Msg msg = (Msg) o;
			Msg.MsgType type = msg.getType();
			if (type == Msg.MsgType.MSG) {
				System.out.println("\n" + msg.getFrom() + " said to " + msg.getTo() + ": " + msg.getContent());
			} else if (type == Msg.MsgType.MSG_ALL) {
				System.out.println("\n" + msg.getFrom() + " said to ALL: " + msg.getContent());
			} else if (type == Msg.MsgType.BROADCAST) {
				System.out.println("\nBROADCAST MSG: " + msg.getContent());
			} else if (type == Msg.MsgType.ONLINE_FRIENDS) {
				String[] arr = msg.getContent().split(" ");
				if (arr != null && arr.length > 0) {
					System.out.println("Online Friends:");
					Arrays.stream(arr).forEach(System.out::println);
				}
			} else if (type == Msg.MsgType.FILE) {
				byte[] data = msg.getData();
				if (data == null)
					continue;

				String dest = String.join("/", BASE_FILE_DIR, msg.getContent());
				try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dest))) {
					bos.write(data);
					System.out.println("\nReceived a file: " + dest);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

}
