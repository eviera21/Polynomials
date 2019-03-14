package edu.uprm.ece.icom4035.list1;
import java.util.Iterator;
//
public class ArrayList<E> implements List<E>{
	private E[] elements;
	private int size;
	private static final int INITCAP = 5;
	public ArrayList() {
		elements = (E[]) new Object[INITCAP];
		size = 0;
	}
	public ArrayList(int length) {
		elements = (E[]) new Object[length];
		size = 0;
	}
	@Override
	public Iterator<E> iterator() {
		return new ALIterator();
	}

	@Override
	public void add(E obj) {
		add(size, obj);
	}

	@Override
	public void add(int index, E obj) {
		if (size+1 >= elements.length) extendAL();
		else if(index >= elements.length) extendAL(index-elements.length+5);
		
		if(index<size) for (int i = size; i > index; i--) {
			elements[i] = elements[i - 1];
		}else for(int i=size;i<index;i++) {
			elements[i]=null;
			size++;
		}
		elements[index] = obj;
		size++;
	}

	@Override
	public boolean remove(E obj) {
		if(!isEmpty()) for (int i=0;i<size;i++) if(elements[i].equals(obj)) return remove(i);
		return false;
	}

	@Override
	public boolean remove(int index) {
		if(index<0||index>=size) throw new IndexOutOfBoundsException("Invalid index.");
		for(int i=index;i<size;i++) {
			elements[i]=null;
			elements[i]=elements[i+1];
		}
		size--;
		return true;
	}

	@Override
	public int removeAll(E obj) {
		int removed=0;
		for(int i=0;i<size;i++) {
			if(elements[i].equals(obj)) {
				remove(i);
				removed++;
			}
		}
		size-=removed;
		return removed;
	}

	@Override
	public E get(int index) {
		if(index>=0 && index<size) return elements[index];
		throw new IndexOutOfBoundsException("Invalid index.");
	}

	@Override
	public E set(int index, E obj) {
		E replaced=null;
		if(index>=0&&index<size) {
			replaced=elements[index];
			elements[index]=(E) obj;
		}
		return replaced;
	}

	@Override
	public E first() {		
		if(isEmpty()) return null;
		return elements[0];
	}

	@Override
	public E last() {
		if(isEmpty()) return null;
		return elements[size-1];
	}

	@Override
	public int firstIndex(E obj) {
		if(!isEmpty()) for(int i=0;i<size;i++) if(elements[i].equals(obj)) return i;
		return -1;
	}

	@Override
	public int lastIndex(E obj) {
		if(!isEmpty()) for(int i=size;i>=0;i--) if(elements[i].equals(obj)) return i;
		return -1;
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
		if(!isEmpty()) for(E e:elements) if (e.equals(obj)) return true;
		return false;
	}

	@Override
	public void clear() {
		for(E e:elements) e=null;
		size=0;
	}

	private void extendAL() {
		extendAL(5);
	}
	private void extendAL(int j) {
		E[] newList = (E[]) new Object[elements.length + j];
		for (int i = 0; i < size; i++) {
			newList[i] = elements[i];
		}
		elements = newList;
		newList=null;
	}
	
	public String print() {
		String s="";
		for(int i=0;i<size;i++) {
			if(i==size-1) s=s+elements[i];
			else s=s+elements[i]+", ";
		}
		return "["+s+"]";
	}


	private class ALIterator implements Iterator<E> {
		private int current;
		private boolean canRemove;
	
		public ALIterator() {
			current = 0;
			canRemove = false;
		}
	
		@Override
		public boolean hasNext() {
			return size>current;
		}
	
		@Override
		public E next() {
			if(!hasNext()) throw new IllegalStateException("Iterator is completed.");
			canRemove=true;
			return get(current++);
		}
		
		public void remove() throws UnsupportedOperationException{
			if(!canRemove) throw new IllegalStateException("Invalid to remove.");
			ArrayList.this.remove(--current);
			canRemove=false;
		}
	
	}
}

