package us.hcheng.javaio.learnhspedu.chapters.chapter19.inputstream;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import us.hcheng.javaio.learnhspedu.chapters.chapter19.entity.Dog;

public class ObjectInputStream_ {

	public static void main(String[] args) {
		String src = "/Users/hongyu.cheng/Desktop/obj.txt";

		try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(src))) {
			/**
			 * have to follow the order!!!
			 * 	1 outputStream.writeInt(13);
			 * 	2 outputStream.writeBoolean(true);
			 * 	3 outputStream.writeChars("Moka");
			 * 	4 outputStream.writeFloat(13.3F);
			 * 	5 outputStream.writeChar('C');
			 * 	6 outputStream.writeUTF("程程程");
			 * 	7 outputStream.writeObject(new int[]{1,3,5});
			 * 	8 outputStream.writeObject(new Dog("Moka", 5, 50));
			 */
			System.out.println("1 readInt: " + inputStream.readInt());
			System.out.println("2 readBoolean: " + inputStream.readBoolean());
			// System.out.println("3 readUTF from writeChars: " + inputStream.readUTF());
			System.out.println("4 readFloat: " + inputStream.readFloat());
			System.out.println("5 readChar: " + inputStream.readChar());
			System.out.println("6 readUTF: " + inputStream.readUTF());

			Object obj = inputStream.readObject();
			if (obj instanceof int[])
				System.out.println("7 readObject: " + Arrays.toString((int[]) obj));

			Object d = inputStream.readObject();
			if (d instanceof Dog) {
				/**
				 * the serialized dog has not implemented bark() but still can be called
				 * with the current version of Dog class ;-)
				 */
				Dog dog = (Dog) d;
				System.out.println("8 readObject: " + dog);
				dog.bark();
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}

	}

}
