package us.hcheng.javaio.learnhspedu.chapters.qq.common.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
}
