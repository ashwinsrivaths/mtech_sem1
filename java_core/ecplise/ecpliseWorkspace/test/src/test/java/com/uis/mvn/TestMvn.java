package com.uis.mvn;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;


public class TestMvn {
	
	@Test
	public void testTesting() {
		System.out.println("testing testing using Junit///////////////////////////////////////!!!!!!!!!!!!");
		
		
		assertEquals("asdf", "asdf");
		
	}
	
	@Test
	public void testGetProperty() throws IOException {
		App a = new App();
		String prop = a.getProperty("abcd");
		
		assertEquals(prop, "property not found");
		
		
		prop = a.getProperty("key1");
		assertEquals("123413124", prop);
	}

}
