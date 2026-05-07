package com.uis.list;

public class MyArrayList {
	private int count = 0;
	private Object[] arr = new Object[10];
	
	public boolean add(Object e) {
		try {
			if(count == arr.length) {
				Object[] temp = arr;
				this.arr = new Object[temp.length * 2];
				
				System.arraycopy(temp, 0, arr, 0, count);
			}
			
			arr[count] = e;
			count++;
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	public int size() {
		return count;
	}
	
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof MyArrayList) {
		
			MyArrayList a1 = (MyArrayList) o;
			
//			Note that I as ArrayList designer can access a different ArrayList private member directly
			if(count == a1.count) {
				return true;
			} else {
				return false;
			}
		
		
		} else {
			return false;
		}
	}
	
	
	
	
	public boolean contains(Object o) {
		for (Object obj:arr) {
			if(obj.equals(o)) {
				return true;
			}
		}
		
		return false;
	}
	
	
	public boolean remove(Object o) {
		for(int i = 0; i< count; i++) {
			if(arr[i].equals(o)) {
				for(int j = i+1; j<count; j++) {
					arr[j-1] = arr[j];
				}
				count--;
				arr[count]=null;
				return true;
			}
		}
		return false;
	}
	
	
}
