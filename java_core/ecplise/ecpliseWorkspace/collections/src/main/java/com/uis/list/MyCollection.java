package com.uis.list;

public interface MyCollection {
//	default access in interface is public
	boolean add(Object ele);

	boolean contains(Object ele);

	boolean remove(Object ele);

	int size();

//	Iterator iterator();
	void clean();

	boolean isEmpty();

	boolean addAll(MyCollection c);

	boolean removeAll(MyCollection c);

	boolean retainAll(MyCollection c);

	boolean containsAll(MyCollection c);

	Object[] toArray();
}
