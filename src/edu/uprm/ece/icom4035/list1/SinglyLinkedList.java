package edu.uprm.ece.icom4035.list1;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SinglyLinkedList<E> implements List<E>{
	private Node<E> first;
	private Node<E> last;
	private int size;
	
	public SinglyLinkedList() {
		first=new Node<E>();
		last=first;
		size=0;
	}
	
	@Override
	public Iterator iterator() {
		return new SLLIterator(this);
	}

	@Override
	public void add(E obj) {
		last.setNext(new Node<E>(obj));
		last=last.getNext();
		size++;
	}

	@Override
	public void add(int index, E obj) {
		Node<E> newNode = new Node<E>(obj);
		if(index<0) throw new IndexOutOfBoundsException();
		while(index>size) add(null);
		if(index==0||index==size) {
			add(obj);
		}
		else {
			Node<E> prev = findNode(index-1);
			newNode.setNext(prev.getNext());
			prev.setNext(newNode);
			size++;
		}
	}

	@Override
	public boolean remove(E obj) {
		Node<E> current = first;
		while(current!=last) {
			if (!current.getNext().getElement().equals(obj)) {
				current = current.getNext();
			}else {
				Node<E> tbr = current.getNext();
				current.setNext(tbr.getNext());
				if(tbr == last) last = current;
				tbr.setElement(null);
				tbr.setNext(null);
				size--;
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean remove(int index) {
		if(index<0&&index>=size) throw new IndexOutOfBoundsException();
		if(index==0) {
			remove(first.getNext().getElement());
			return true;
		}
		Node<E> current = findNode(index-1);
		Node<E> tbr = current.getNext();
		current.setNext(tbr.getNext());
		if(tbr==last) last = current;
		tbr.setElement(null);
		tbr.setNext(null);
		size--;
		return true;
	}

	@Override
	public int removeAll(E obj) {
		int removed=0;
		Node<E> current=first.getNext();
		while (current.getElement().equals(obj)) { 
			remove(current.getElement());
			removed++;
			current = first.getNext();
		}
		while(current!=last) {
			if(current.getNext().getElement().equals(obj)) {
				Node<E> tbr = current.getNext();
				current.setNext(tbr.getNext());
				if(tbr==last) last = current;
				tbr.setNext(null);
				tbr.setElement(null);
				removed++;
				size--;
				
			}else current = current.getNext();
		}
		return removed;
	}

	@Override
	public E get(int index) {
		if(index>=size||index<0) throw new IndexOutOfBoundsException();
		return findNode(index).getElement();
	}

	@Override
	public E set(int index, E obj) {
		if(index<0||index>=size) throw new IndexOutOfBoundsException();
		Node<E> current = findNode(index);
		E e = current.getElement();
		current.setElement(e);
		return e;
	}

	@Override
	public E first() {
		if(isEmpty()) return null;
		return first.getNext().getElement();
	}

	@Override
	public E last() {
		if(isEmpty()) return null;
		return last.getElement();
	}

	@Override
	public int firstIndex(E obj) {
		if(isEmpty()) return -1;
		Node<E> current = first.getNext();
		int index=0;
		while (current!=last) {
			if(!current.getElement().equals(obj)) {
				current = current.getNext();
				index++;
			}
			else return index;
		}
		return -1;
	}

	@Override
	public int lastIndex(E obj) {
		if(isEmpty()) return -1;
		int index = -1;
		Node<E> current = first.getNext();
		for(int i=0;i<size;i++) {
			if(!current.isEmpty()&&current.getElement().equals(obj)) index=i;
			current = current.getNext();
		}
		return index;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size==0;
	}

	@Override
	public boolean contains(E obj) {
		return findNode(obj)!=null;
	}

	@Override
	public void clear() {
		Node<E> current = first;
		while (current!=last) {
			current.getNext().setElement(null);
			current=current.getNext();
		}
		
	}
	
	private Node<E> findNode(E e){
		if(isEmpty()) return null;
		Node<E> current = first.getNext();
		for(int i=0;i<size;i++) {
			if (current.getElement().equals(e)) return current;
			else current = current.getNext();
		}
		return null;
	}
	
	private Node<E> findNode(int index){
		if(isEmpty())return null;
		Node<E> current = first.getNext();
		if(index==-1) return first;
		for(int i=0;i<index;i++) current = current.getNext();
		return current;
	}
	
	public String print() {
		String s="[";
		Node<E> current=first;
		for(int i=-1;i<size;i++) {
			if(current==last) s=s+current.getElement()+"]";
			else s=s+current.getElement()+", ";
			current=current.getNext();
		}
		return s;
	}
	



	private class SLLIterator implements Iterator<E>{
		private SinglyLinkedList<E> sllist;
		private boolean canRemove;
		private Node<E> node;
		private Node<E> ptntr;
		
		SLLIterator(SinglyLinkedList<E> e) {
			sllist = e;
			canRemove=false;
			node=(Node<E>) first;
			ptntr=null;
		}
	
		@Override
		public boolean hasNext() {
			return node.getNext()!=null;
		}
	
		@Override
		public E next() {
			if(!hasNext()) throw new NoSuchElementException("Iterator is complete.");
			if (canRemove) ptntr = (ptntr == null ? first.getNext() : ptntr.getNext());
			canRemove=true;
			node=node.getNext();
			return node.getElement();
		}
		public void remove() throws UnsupportedOperationException{
			if (!canRemove) throw new IllegalStateException("Not valid to remove.");
			if(ptntr == null) first.getNext().setNext(first.getNext().getNext());
			else ptntr.setNext(ptntr.getNext().getNext());
			size--; canRemove = false;
		}
		
	}
}
 
