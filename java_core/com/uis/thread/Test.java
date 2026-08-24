package com.uis.thread;

public class Test {
    public static void main(String[] args) {
        // Thread creation using Thread class
        BingoThread bingoThread = new BingoThread();
        bingoThread.setName("BingoThread");
        bingoThread.setPriority(Thread.MIN_PRIORITY);
        bingoThread.start();

        // Thread creation using Runnable interface
        BongoThread bongoThread = new BongoThread();
        Thread thread1 = new Thread(bongoThread);
        thread1.setName("BongoThread");
        thread1.setPriority(Thread.MAX_PRIORITY);
        thread1.start();
        
    }
}
