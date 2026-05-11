package test1;

import java.util.Arrays;

interface Vok {
    public Boolean trans(String msg) throws MyException;

    public String res();
}

class Vok1 implements Vok, Runnable {
    @Override
    public Boolean trans(String msg) throws MyException {
        // || or is a short circuit operator,
        // it will not evaluate the second condition if the first condition is true
        // if we call null.trim() it will throw NullPointerException, so we need to
        // check for null first
        if (msg == null || msg.trim().equals("")) {
            throw new IllegalArgumentException("issue with input: " + msg

            );
        }
        try {
            System.out.println(msg);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }

    }

    @Override
    public String res() {
        return "Response from Vok1";
    }

    @Override
    public void run() {
        // Implementation for Runnable interface

    }

    public String thread1(String name) {

        return "Thread1: " + name;
        // synchronized block to ensure thread safety
    }
}

public class Test {
    public static void main(String[] args) {

        try {
            Vok vok = new Vok1();
            vok.trans("Hello from Vok!");
            String result = vok.res();
            System.out.println("Result: " + result);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        String s = "Hello World from Java";

        int[] arr = { 5, 2, 8, 1, 3 };

        System.out.println(func(0, arr));
        System.out.println(func2(arr, 0, arr.length-1));


        System.out.println(func3(12345));
    }

    static int func(int a, int[] arr) {

        if (arr.length == 0)
            return a;
        if (arr.length == 1)
            return a > arr[0] ? a : arr[0];

        int left = func(a, Arrays.copyOfRange(arr, 0, arr.length / 2));
        return func(a > left ? a : left, Arrays.copyOfRange(arr, arr.length / 2, arr.length));
    }

    static int func2(int[] arr, int left, int right) {
        if(left == right) return arr[left];

        return func2(arr, left, (left+right)/2) + func2(arr, ((left+right)/2) + 1, right);
    }


    static int func3(int i){
        if (i%10 == 0) {
            return i;
        } else {
            return (i%10) + func3((Integer) i/10);
        }
    }

}


/**
 * do not return normally to invoker if bad inputs is given
 * always perform input validation and throw exception if input is invalid
 * do not catch exception if you cannot handle it, let it propagate to the
 * caller
 * do not catch generic exception, catch specific exceptions
 * always clean up resources in finally block or use try-with-resources
 * do not use exceptions for control flow, use them for exceptional conditions
 * only
 * release third party resources in finally block
 * only release third party resources in finally block and do not return
 * anything as it overrides other return statements in the try block and
 * exceptions that are thrown
 * DO NOT USE FINALLY CODE BLOCK FOR BUSINESS LOGIC
 * 
 * 
 */