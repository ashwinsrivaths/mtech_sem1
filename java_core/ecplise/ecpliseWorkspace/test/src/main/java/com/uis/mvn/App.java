package com.uis.mvn;

import java.io.IOException;
import java.util.Properties;

public class App {
	
	private Properties myProperties;
	
	public App() throws IOException {
		myProperties = new Properties();
		myProperties.load(App.class.getClassLoader().getResourceAsStream("credential.properties"));
		
	}
	
	
	public String getProperty(String key) {
		if(myProperties.containsKey(key)) {
			String prop = (String) myProperties.getProperty(key);
			return prop;
		} else {
			return "property not found";
		}
	}
	

	public static void main(String[] args) {
		System.out.println("--------------> main method test project mvn package");

	}

}
