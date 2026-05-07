package com.uis.list;

public class Stack {
	
	private MyLinkedList lst = new MyLinkedList();
	
	public void push(Object ele) {
		lst.add(ele);
	}
	
	public Object pop() {
		return lst.remove(lst.size());
	}
	
	public boolean isEmpty() {
		return lst.size()==0;
	}

}
