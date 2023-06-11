package us.hcheng.javaio.learnhspedu.chapters.chapter19.homework;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Properties;
import lombok.AllArgsConstructor;
import lombok.Data;

public class Homework03 {

	public static void main(String[] args) throws Exception {
		part2(part1());
		part3();
	}

	private static Dog part1() throws IOException {
		Properties prop = new Properties();
		prop.load(new FileReader("/Users/nikkima/Desktop/mytemp/dog.properties"));
		Dog dog = new Dog(prop.getProperty("name"), Integer.parseInt(prop.getProperty("age")), prop.getProperty("color"));
		System.out.println("part1: " + dog);
		return dog;
	}

	private static void part2(Dog dog) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream("/Users/nikkima/Desktop/mytemp/dog.bat");
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		//BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(objectOutputStream);
		objectOutputStream.writeObject(dog);
		System.out.println("part2: done!");
	}

	private static void part3() throws IOException, ClassNotFoundException {
		FileInputStream fileInputStream = new FileInputStream("/Users/nikkima/Desktop/mytemp/dog.bat");
		ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
		//BufferedInputStream bufferedInputStream = new BufferedInputStream(objectInputStream);
		System.out.println("part3: " + objectInputStream.readObject());
	}

}

@AllArgsConstructor
@Data
class Dog implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private int age;
	private String color;
}
