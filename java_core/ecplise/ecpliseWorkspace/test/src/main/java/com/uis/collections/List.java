package com.uis.collections;

import java.util.ArrayList;
import java.util.Collection;

public class List {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		Collection col = new ArrayList<>();
		
		for(String str: args) {
			col.add(str);
		}
		
		
		System.out.println(col);
		
		
		

	}

}
