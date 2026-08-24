package com.uis.test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TestMap {
    public static void main(String[] args) {

        String str = "the distance between jhonson and and and and and jhonson are not the same";
        String[] words = str.split(" ");
        Map<String, Integer> wc = new HashMap<>();

        for (String word : words) {
            if (wc.containsKey(word)) {
                Integer i = wc.get(word);
                wc.put(word, ++i);
            } else {
                wc.put(word, 1);
            }
        }

        System.out.println(wc);


        // not good as n^2 complexity
        // Collections.frequiency loops over the list internally
        for (String w : words) {
            int count = Collections.frequency(Arrays.asList(words), w);

            System.out.println(w + " occurs => " + count);
        }
    }
}
