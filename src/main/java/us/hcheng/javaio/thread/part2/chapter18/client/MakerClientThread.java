package us.hcheng.javaio.thread.part2.chapter18.client;

import java.util.Random;

import com.cheng.c18.ActiveObject;
import com.cheng.c18.entity.Result;

public class MakerClientThread extends Thread {

	private final ActiveObject fActiveObject;

	private static final char[] CHARS = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'K'};
	private static final Random random = new Random(System.currentTimeMillis());

	public MakerClientThread(ActiveObject activeObject, String name) {
		super(name);
		fActiveObject = activeObject;
	}


	@Override
	public void run(){
		try {
			for(int i = 0; true; i++){
				Result result = fActiveObject.makeString(CHARS[random.nextInt(10)], i);
				Thread.sleep(10);
				Object value = result.getResultValue();
				System.out.println(Thread.currentThread().getName() + ":  MakerClientThread says value = " + value);
			}
		} catch (InterruptedException e){
			System.out.println("MakerClientThread run InterruptedException ex: " + e);
		}
	}

}
