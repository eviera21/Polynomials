package edu.uprm.ece.icom4035.list1;

public class Node<E>{
	private E element;
	private Node<E> next;
	public Node(E e){
		element = e;
	}
	public Node() {
		element=null;
		next=null;
	}
	
	public E getElement() {
		return element;
	}
	public Node<E> getNext(){
		return next;
	}
	public void setElement(E e) {
		element = e;
	}
	public void setNext(Node<E> n) {
		next = n;
	}
	public boolean isEmpty() {
		return element == null;
	}
}