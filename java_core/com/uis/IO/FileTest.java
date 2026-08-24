package com.uis.IO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class FileTest {
    

    public static void main(String[] args) {
        

        Scanner sc1 = new Scanner(System.in);

        String path = sc1.nextLine();

        File file = new File(path);

        if(file.exists() && file.isFile() && file.getName().endsWith(".txt")){
            System.out.println("yesssssssssssssssss");


            // NOTE THAT BUFFERED READER IS BETTER THAN FILE READER AS IT BUFFERES THE DATA AND INCREASES THRUPUT
            BufferedReader br = null;
            BufferedWriter bw = null;
            try {
                br = new BufferedReader(new FileReader(file));

                String line = br.readLine();
                while(line != null){
                    
                }

            } catch (Exception e) {
                // TODO: handle exception
            } finally {
                try {
                    
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }




        } else {
            System.out.println("noooooooooooooooo");
        }
    }
}
