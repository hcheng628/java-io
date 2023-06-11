package us.hcheng.javaio.learnhspedu.chapters.chapter19.outputstream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import us.hcheng.javaio.learnhspedu.chapters.chapter19.entity.Dog;

public class ObjectOutputStream_ {

	public static void main(String[] args) {
		String dest = "/Users/nikkima/Desktop/obj.txt";

		try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(dest))) {
			outputStream.writeInt(13);
			outputStream.writeBoolean(true);
			outputStream.writeFloat(13.3F);
			outputStream.writeChar('C');
			outputStream.writeUTF("程程程");
			outputStream.writeObject(new int[]{1,3,5});
			outputStream.writeObject(new Dog("Moka", 5, 50));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
