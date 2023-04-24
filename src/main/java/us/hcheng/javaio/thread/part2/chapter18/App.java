package us.hcheng.javaio.thread.part2.chapter18;

import com.cheng.c18.client.DisplayClientThread;
import com.cheng.c18.client.MakerClientThread;

public class App {

	public static void main(String[] args) {
		ActiveObject activeObject = ActiveObjectFactory.getActiveObject();
		new MakerClientThread(activeObject, "Alice").start();
		new MakerClientThread(activeObject, "Bobby").start();
		new DisplayClientThread(activeObject, "Chris").start();
	}

}
