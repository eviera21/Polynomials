package edu.uprm.ece.icom4035.list1;

public class SinglyLinkedListFactory<E> implements ListFactory<E> {
	@Override
	public SinglyLinkedList<E> newInstance() {
		return new SinglyLinkedList<E>();
	}
}
