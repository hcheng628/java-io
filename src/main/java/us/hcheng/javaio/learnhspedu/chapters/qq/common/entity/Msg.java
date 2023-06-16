package us.hcheng.javaio.learnhspedu.chapters.qq.common.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Msg implements Serializable {
	private String from;
	private String to;
	private String content;
	private String timestamp;
	private byte[] data;
	private MsgType type;

	public Msg(MsgType type) {
		this.type = type;
	}

	public enum MsgType {
		FILE, MSG, MSG_ALL, LOGIN, LOGIN_FAIL, LOGOUT, ONLINE_FRIENDS,
	}

}
