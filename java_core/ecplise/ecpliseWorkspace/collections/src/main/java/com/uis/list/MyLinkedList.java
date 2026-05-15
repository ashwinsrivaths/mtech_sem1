package com.uis.list;

import java.util.HashSet;
import java.util.Objects;

public class MyLinkedList implements MyList {

	private Node head, tail;
	private int count = 0;

	public MyLinkedList() {
		super();
	}

	@Override
	public boolean add(Object element) {
		if (element == null) {
			return false;
		}
		try {
			if (head == null || tail == null || count == 0) {
				head = new Node(null, null, element);
				tail = head;
			} else {
				tail.setNext(new Node(tail, null, element));
				tail = tail.getNext();
			}

			count++;
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean add(int position, Object element) {
		if (element == null) {
			return false;
		}
		if (position < 0 || position > count) {
			// throw new IllegalArgumentException("position should be greater than 1");
			return false;
		}

		if (position == 0) {
			if (head == null || tail == null || count == 0) {
				return add(element);
			} else {
				head = new Node(null, head, element);
				head.getNext().setPrevious(head);
			}
			count++;
			return true;
		}
		if (position == count) {
			return add(element);
		}
		Node current;
		if (position > count / 2) {

			current = tail;
			for (int i = count; i > position; i--) {
				current = current.getPrevious();

			}
		} else {
			current = head;
			for (int i = 0; i < position; i++) {
				current = current.getNext();
			}
		}
		Node prev = current.getPrevious();
		Node newNode = new Node(prev, current, element);
		prev.setNext(newNode);
		current.setPrevious(newNode);
		count++;
		return true;
	}

	@Override
	public boolean contains(Object element) {
		Node current = head;
		while (current != null) {
			if (current.getValue() != null && current.getValue().equals(element)) {
				return true;
			}
			current = current.getNext();
		}
		return false;
	}

	@Override
	public String toString() {
		if (head == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder("");
		Node current = head;
		while (current != null) {
			sb.append(current.getValue().toString());
			current = current.getNext();
			if (current != null) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}

//	public static void main(String[] args) {
//
//		Collection col = new ArrayList<>();
//		for (String str : args) {
//			col.add(str);
//		}
//		System.out.println(col);
//		List l = (List) col;
//		System.out.println(l.get(1));
//	}

	@Override
	public Object remove(int pos) {
		if (head == null || pos < 0) {
			return null;
		}
		int cnt = 0;
		Node current = head;
		while (current != null) {
			if (cnt == pos) {

				Node previous = current.getPrevious();
				Node next = current.getNext();
				if (previous != null) {
					previous.setNext(next);
				} else {
					head = next;
				}

				if (next != null) {
					next.setPrevious(previous);
				} else {
					tail = previous;
				}
				this.count--;
				return current.getValue();
			}
			current = current.getNext();
			cnt++;
		}

		return null;
	}

	@Override
	public int indexOf(Object ele) {
		if (head == null || ele == null) {
			return -1;
		}
		int cnt = 0;
		Node current = head;
		while (current != null) {
			if (current.getValue() != null && current.getValue().equals(ele)) {
				return cnt;
			}
			current = current.getNext();
			cnt++;
		}
		return -1;
	}

	@Override
	public Object get(int pos) {
		if (head == null || pos < 0) {
			return null;
		}
		int cnt = 0;
		Node current = head;
		while (current != null) {
			if (cnt == pos) {
				return current.getValue();
			}
			current = current.getNext();
			cnt++;
		}
		return null;
	}

	@Override
	public boolean remove(Object ele) {
		if (head == null || ele == null) {
			return false;
		}
		Node current = head;
		while (current != null) {
			if (current.getValue() != null && current.getValue().equals(ele)) {

				Node previous = current.getPrevious();
				Node next = current.getNext();
				if (previous != null) {
					previous.setNext(next);
				} else {
					head = next;
				}

				if (next != null) {
					next.setPrevious(previous);
				} else {
					tail = previous;
				}
				this.count--;
				return true;
			}
			current = current.getNext();
		}
		return false;
	}

	@Override
	public int size() {
		return this.count;
	}

	@Override
	public void clean() {
		this.head = null;
		this.tail = null;
		this.count = 0;
	}

	@Override
	public boolean isEmpty() {
		if (this.head == null || this.count == 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean addAll(MyCollection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(MyCollection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(MyCollection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(MyCollection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object[] toArray() {
		if (head == null) {
			return new Object[0];
		}
		Object[] arr = new Object[this.count];
		Node current = head;
		int cnt = 0;
		while (current != null) {
			arr[cnt] = current.getValue();
			cnt++;
			current = current.getNext();
		}
		return arr;
	}

	public boolean reverse() {
		if (head == null || tail == null) {
			return false;
		}
		Node current = this.tail;
		Node temp1;
		while (current != null) {
			temp1 = current.getPrevious();
			current.setPrevious(current.getNext());
			current.setNext(temp1);
			current = temp1;
		}

		temp1 = head;
		head = tail;
		tail = temp1;
		head.setPrevious(null);
		tail.setNext(null);

		return true;
	}

	public Object midEleWithoutCnt() {
//		left middle
		Node a = head;
		Node b = head;

		while (b != null && b.getNext() != null && b.getNext().getNext() != null) {
			a = a.getNext();
			b = b.getNext().getNext();
		}

		return a.getValue();

	}

	public boolean cyclePresent() {
		Node current = head;
		HashSet nodeSet = new HashSet();

		while (current != null) {
			if (nodeSet.add(current) == false) {
				return true;
			}
			current = current.getNext();
		}

		return false;

	}

	public void printInv() {
		if (this.head == null) {
			return;
		} else {
			myPrint(this.head);

		}
	}

	private void myPrint(Node n) {
		if (n.getNext() != null) {
			myPrint(n.getNext());
		}
		System.out.println(n.getValue());
	}

}

class Node {

	private Node previous, next;
	private Object value;

	public Node(Node previous, Node next, Object value) {
		super();
		this.previous = previous;
		this.next = next;
		this.value = value;
	}

	public Node getPrevious() {
		return previous;
	}

	public void setPrevious(Node previous) {
		this.previous = previous;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(next, previous, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		return Objects.equals(next, other.next) && Objects.equals(previous, other.previous)
				&& Objects.equals(value, other.value);
	}

}
