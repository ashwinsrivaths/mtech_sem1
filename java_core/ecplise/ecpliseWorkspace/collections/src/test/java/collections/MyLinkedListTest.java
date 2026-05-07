package collections;

import com.uis.list.MyLinkedList;

import static org.junit.Assert.assertEquals;

import org.junit.Test;



public class MyLinkedListTest {
	
	
//	@Test
//	public void toStringTesting() {
//		MyLinkedList mls = new MyLinkedList();
//		
//		mls.add("1");
//		mls.add("1");
//		mls.add("2");
//		mls.add("3");
//		mls.add("4");
//		mls.add("5");
//		mls.add("1");
//		
//		System.out.println(mls.toString());
//		
//		System.out.println("1".toString());
//		
//		assertEquals(mls.toString(), "1, 1, 2, 3, 4, 5, 1");
//	}
	
	
	
	@Test
	public void printInv() {
		MyLinkedList mls = new MyLinkedList();
		
		mls.add("1");
		mls.add("1");
		mls.add("2");
		mls.add("3");
		mls.add("4");
		mls.add("5");
		mls.add("1");
		
		mls.printInv();
		
		
//		assertEquals(mls.toString(), "1, 1, 2, 3, 4, 5, 1");
	}
	
	
}
