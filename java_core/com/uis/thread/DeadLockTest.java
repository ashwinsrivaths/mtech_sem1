package com.uis.thread;

/**
 * ensure ordering of locks in the jobs in the same order!!!
 */
public class DeadLockTest {
    public static void main(String[] args) {
        System.out.println("Main starting");
        String lock1 = "str1";
        String lock2 = "str2";

        MyBongoRunnable myBongoRunnable = new MyBongoRunnable(lock1, lock2);
        MyBingoRunnable myBingoRunnable = new MyBingoRunnable(lock1, lock2);

        Thread myBongoThread = new Thread(myBongoRunnable);
        Thread myBingoThread = new Thread(myBingoRunnable);

        // myBongoThread.start();
        // myBingoThread.start();

        // The above code will now be written using anonymous inner class

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                synchronized (lock1) {
                    try {
                        System.out.println("lock1 acquired by " + Thread.currentThread().getName());
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        System.out.println("InterruptedException; got ");
                        e.printStackTrace();
                    }

                    synchronized (lock2) {

                        System.out.println("lock2 acquired by " + Thread.currentThread().getName());

                    }
                }

                System.out.println(Thread.currentThread().getName() + " done execution");

            }
        });

        t1.setName("anonymousInnerClassThread1");
        t1.start();

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                synchronized (lock2) {
                    try {
                        System.out.println("lock2 acquired by " + Thread.currentThread().getName());
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        System.out.println("InterruptedException; got ");
                        e.printStackTrace();
                    }
                    synchronized (lock1) {

                        System.out.println("lock1 acquired by " + Thread.currentThread().getName());

                    }
                }

                System.out.println(Thread.currentThread().getName() + " done execution");

            }
        });

        t2.setName("anonymousInnerClassThread1");
        t2.start();

        System.out.println("main ending .......");

    }
}

class MyBongoRunnable implements Runnable {

    String lock1;
    String lock2;

    public MyBongoRunnable(String s1, String s2) {
        lock1 = s1;
        lock2 = s2;
    }

    @Override
    public void run() {
        synchronized (lock1) {
            try {
                System.out.println("lock1 acquired by " + Thread.currentThread().getName());
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("InterruptedException; got ");
                e.printStackTrace();
            }

            synchronized (lock2) {

                System.out.println("lock2 acquired by " + Thread.currentThread().getName());

            }
        }

        System.out.println(Thread.currentThread().getName() + " done execution");

    }

}

class MyBingoRunnable implements Runnable {

    String lock1;
    String lock2;

    public MyBingoRunnable(String s1, String s2) {
        lock1 = s1;
        lock2 = s2;
    }

    @Override
    public void run() {
        synchronized (lock2) {
            try {
                System.out.println("lock2 acquired by " + Thread.currentThread().getName());
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("InterruptedException; got ");
                e.printStackTrace();
            }
            synchronized (lock1) {

                System.out.println("lock1 acquired by " + Thread.currentThread().getName());

            }
        }

        System.out.println(Thread.currentThread().getName() + " done execution");

    }

}