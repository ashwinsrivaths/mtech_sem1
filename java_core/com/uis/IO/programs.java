package com.uis.IO;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;

// /home/ashwin/Documents/old/temp.txt

public class programs {
    public static void main(String[] args) {

        System.out.println("enter file path");

        Scanner sc1 = new Scanner(System.in);

        String path = sc1.nextLine();

        File f = new File(path);

        System.out.println("current files in folder");
        for (File file : f.listFiles()) {
        System.out.println(file.getName());
            
        }


        if (f.exists() && f.isFile() && f.getName().endsWith(".txt")) {
            // noOfOccurancesOfEachWord(f);

            // noOfOccurancesOfCharInString(
            //         "haskdfhasjkdfhsadkfjweuhrfsadjksadfgasd35f4asd53453sad4f534asdf534sad5ff5sd3   sdfsdf  `12345890-=``112345678/*-';;");


            // copyUsingStream(f);





        } else {
            System.out.println("file path needed ...");
        }

    }

    // program 1
    public static void printContents(File f) {

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(f));
            String str = br.readLine();
            while (str != null) {

                // program 1 to print all lines from file
                System.out.println(str);

                str = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (Exception e) {
                System.err.println("oh noo could not close file");
            }
        }
    }

    // program 2
    public static void printSearchLine(File f, String str) {
        LineNumberReader lr = null;
        int count = 0;

        try {
            lr = new LineNumberReader(new BufferedReader(new FileReader(f)));
            String line = lr.readLine();

            while (line != null) {
                if (line.contains(str)) {
                    int index = line.indexOf(str);
                    while (index != -1) {
                        count++;
                        index = line.indexOf(str, ++index);
                    }
                    System.out.println("line no " + lr.getLineNumber() + ": " + line);
                }
                line = lr.readLine();
            }
        } catch (Exception e) {
            System.out.println("exe");
        } finally {
            try {
                lr.close();
            } catch (Exception e) {
                System.out.println("exe");
            }
        }

        System.out.println(count);
    }

    // program 3 program sort words
    public static void sortWords(File f) {
        LineNumberReader lr = null;
        int count = 0;
        TreeSet ts = new TreeSet<String>();
        try {
            lr = new LineNumberReader(new BufferedReader(new FileReader(f)));
            String line = lr.readLine();

            while (line != null) {

                for (String s : line.split(" ")) {
                    ts.add(s);
                }
                line = lr.readLine();
            }
        } catch (Exception e) {
            System.out.println("exe");
        } finally {
            try {
                lr.close();
            } catch (Exception e) {
                System.out.println("exe");
            }
        }
        System.out.println(ts);
    }

    // program 4 replace input with another
    public static void replace(File f, String s1, String s2) {
        BufferedReader br = null;
        BufferedWriter bw = null;

        try {
            br = new BufferedReader(new FileReader(f));
            bw = new BufferedWriter(new FileWriter(new File("./testOutput.txt")));
            String line = br.readLine();
            while (line != null) {
                bw.write(line.replace(s1, s2));
                bw.newLine();
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // NOTE: NO RETURNING OR THROWING OF ERROR IN FINALLY LEADING TO SILENT FAILURE
            // WE CAN ONLY LOG THE ISSUE
            try {
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // program 4 replace input with another in same file
    public static void replaceSameFile(File f, String s1, String s2) {
        BufferedReader br = null;
        List<String> lst = new LinkedList<String>();

        try {
            br = new BufferedReader(new FileReader(f));
            String line = br.readLine();
            while (line != null) {
                // NOTE THAT LINE ONLY WILL NOT BE REPLACED AND A NEW OBJECT WILL BE RETURNED AS
                // STRINGS ARE IMMUTABLE
                lst.add(line.replace(s1, s2));
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // NOTE: NO RETURNING OR THROWING OF ERROR IN FINALLY LEADING TO SILENT FAILURE
            // WE CAN ONLY LOG THE ISSUE
            try {
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(f, false));

            while (!lst.isEmpty()) {
                bw.write(lst.remove(lst.size() - 1));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // NOTE: NO RETURNING OR THROWING OF ERROR IN FINALLY LEADING TO SILENT FAILURE
            // WE CAN ONLY LOG THE ISSUE
            try {
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void noOfOccurances(File f, String word) {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(f));

            String line = br.readLine();
            int cnt = 0;
            while (line != null) {
                for (String w : line.split(" ")) {
                    if (w.equals(word)) {
                        cnt++;
                    }
                }

                line = br.readLine();
            }

            System.out.println(cnt);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void noOfOccurancesOfEachWord(File f) {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(f));

            String line = br.readLine();
            Map<String, Integer> map = new HashMap<String, Integer>();
            while (line != null) {
                for (String w : line.split(" ")) {

                    Integer value = map.get(w);
                    if (value != null) {
                        map.put(w, value + 1);
                    } else {
                        map.put(w, 1);
                    }
                }

                line = br.readLine();
            }

            System.out.println(map);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void noOfOccurancesOfCharInString(String str) {
        int[] arr = new int[150];
        for (char c : str.toCharArray()) {
            try {
                arr[c]++;
            } catch (Exception e) {
                System.out.println("unhandled character");
            }

        }

        System.out.println(Arrays.toString(arr));
    }

    public static void copyUsingStream(File f) {
        BufferedInputStream bin = null;
        BufferedOutputStream bout = null;

        try {
            bin = new BufferedInputStream(new FileInputStream(f));
            bout = new BufferedOutputStream(new FileOutputStream("./a.txt"));

            int byteWord = bin.read();
            while (byteWord != -1) {
                bout.write(byteWord);
                byteWord = bin.read();

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bin.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            try {
                bout.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
