package com.uis.thread;

public class BingoThread extends Thread {
    @Override
    public void run(){
        for (int i = 0; i < 5000; i++) {
            System.out.println("Bingo thread is running: " + i);
        }
    }
}