package com.uis.list;

public interface MyList extends MyCollection {
	boolean add(int pos, Object ele);

	Object remove(int pos);

	int indexOf(Object ele);

	Object get(int pos);
}
