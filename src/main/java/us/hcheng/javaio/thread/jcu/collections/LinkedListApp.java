package us.hcheng.javaio.thread.jcu.collections;

public class LinkedListApp {

	public static void main(String[] args) {
		LinkedList<String> list = LinkedList.of("Moka", "Cheng", "Apple", "logi", "Illy");
		System.out.println(list);

		System.out.println(list.contains("Pot"));

		System.out.println(list.contains("Illy"));
		list.removeFirst();
		System.out.println(list.contains("Illy"));

		System.out.println(list.size());
		System.out.println(list);

		list.addFirst("ThinkPad");
		list.addFirst("MacBook");

		System.out.println(list.size());
		System.out.println(list);
	}

}
