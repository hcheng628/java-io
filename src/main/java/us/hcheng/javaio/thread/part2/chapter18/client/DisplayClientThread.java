package us.hcheng.javaio.thread.part2.chapter18.client;

import us.hcheng.javaio.thread.part2.chapter18.ActiveObject;

public class DisplayClientThread extends Thread {
	private final ActiveObject fActiveObject;

	public DisplayClientThread(ActiveObject activeObject, String name) {
		super(name);
		this.fActiveObject = activeObject;
	}

	@Override
	public void run() {
		try {
			for( int i = 0; true; i++){
				String string = Thread.currentThread().getName() + "  " + i;
				fActiveObject.displayString(string);
				Thread.sleep(200);
			}
		} catch(InterruptedException e) {
			System.out.println("DisplayClientThread run InterruptedException ex: " + e);
		}
	}
}
