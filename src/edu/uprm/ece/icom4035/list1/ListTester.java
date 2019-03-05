package edu.uprm.ece.icom4035.list1;

public class ListTester {
	public static void main(String[]args) {
		ArrayList<Integer> al = new ArrayList<Integer>();
		SinglyLinkedList<Integer> sll = new SinglyLinkedList<Integer>();

		System.out.println(sll.isEmpty());
		try{System.out.println(sll.get(0));}
		catch (Exception e) {System.out.println("invalid index");}
		System.out.println(sll.first());
		System.out.println(sll.last());
		System.out.println(sll.firstIndex(0));
		System.out.println(sll.lastIndex(0));
		System.out.println(sll.size());
		System.out.println(sll.contains(0));
		System.out.println(sll.remove((Integer)4));
		try {sll.remove(0);}
		catch (Exception e) {System.out.println("index out of bounds");}
		sll.add(43);
		System.out.println(sll.print());
		sll.add(0,32);
		System.out.println(sll.print());
		sll.remove((Integer)32);
		sll.add(22);
		System.out.println(sll.print());
		sll.add(3,22);
		System.out.println(sll.print());
		System.out.println(sll.firstIndex(22));
		System.out.println(sll.lastIndex(22));
		sll.remove(1);
		System.out.println(sll.print());
		
	}
}
