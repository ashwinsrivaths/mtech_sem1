package com.uis.InnerClassesAndSingleton;

public class InnerClassTest {


    
    public static void main(String[] args) {
        CommandLogger logger = CommandLogger.getInstance();
        logger.log("This is a test command", CommandLogger.MEDIUM_PRIORITY);
    }
}
