package edu.uprm.ece.icom4035.list1;

public class ArrayListFactory<E> implements ListFactory<E>{
	@Override
	public ArrayList<E> newInstance() {
		return new ArrayList<E>();
	}
}
