package us.hcheng.javaio.learnhspedu.chapters.chapter19.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;

/**
 * serialVersionUID has to match!!! and it will use whatever package.class
 * in deserialization
 */
@AllArgsConstructor
public class Dog implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private Integer age;
	private transient Integer weight;
	private static Boolean alive;

	@Override
	public String toString() {
		return "Dog{" +
				"name='" + name + '\'' +
				", age=" + age +
				", weight=" + weight +
				", alive=" + alive +
				'}';
	}

	public void bark() {
		System.out.println("My name is " + name);
	}

}
