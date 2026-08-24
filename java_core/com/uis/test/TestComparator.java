package com.uis.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;


public class TestComparator {
    public static void main(String[] args) {
        TreeSet ts = new TreeSet<>();
        ts.add("Hello");
        ts.add("Hi");
        ts.add("Welcome");
        ts.add("Hey");

        System.out.println("Default sorting (lexicographical): " + ts);

        TreeSet ts2 = new TreeSet<>(new StringLengthComparator());
        ts2.add("Hello");
        ts2.add("Hi");
        ts2.add("Welcome");
        ts2.add("Hey");
        System.out.println("Sorting by string length: " + ts2);


        List list = new ArrayList<>();
        list.add("Hello");
        list.add("Hi");
        list.add("Welcome");    
        list.add("Hey");

            
        Collections.sort(list);
        System.out.println("Sorted list by natural ordering: " + list);

        Collections.sort(list, new StringLengthComparator());
        System.out.println("Sorted list by string length: " + list);



        String[] arr = {"Hello", "Hi", "Welcome", "Hey"};
        Arrays.sort(arr);
        System.out.println("Sorted array by natural ordering: " + Arrays.toString(arr));
        Arrays.sort(arr, new StringLengthComparator());
        System.out.println("Sorted array by string length: " + Arrays.toString(arr));




        // Collections.
    }
}


class StringLengthComparator implements Comparator{
    @Override
    public int compare(Object o1, Object o2) {
        if (!(o1 instanceof String) || !(o2 instanceof String)) {
            throw new IllegalArgumentException("Both objects must be strings");
        }

        String s1 = (String) o1;
        String s2 = (String) o2;
        return Integer.compare(s1.length(), s2.length());
    }
}