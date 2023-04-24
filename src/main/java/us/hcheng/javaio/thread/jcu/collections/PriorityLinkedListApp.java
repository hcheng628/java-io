package us.hcheng.javaio.thread.jcu.collections;

public class PriorityLinkedListApp {

	public static void main(String[] args) {
		PriorityLinkedList<Integer> list = PriorityLinkedList.of(1,2,3,4,5,7,6,5,5,9);
		System.out.println(list);

		list.removeFirst();
		System.out.println(list);


		list.addFirst(-1);
		System.out.println(list);


		list.addFirst(100);
		System.out.println(list);

		System.out.println(list.contains(100));


		list.removeFirst();
		System.out.println(list);
	}

}
