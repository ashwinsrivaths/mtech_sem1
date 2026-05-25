package com.uis.IO;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CharacterCopy {

    public static void main(String[] args) {
        FileReader fileReader = null;
        FileWriter fileWriter = null;

        try {
            fileReader = new FileReader("./test.txt");
            fileWriter = new FileWriter("./testOut.txt", false);

            int data = fileReader.read();
            while (data != -1) {
                fileWriter.write(data);
                data = fileReader.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.close();
            } catch (Exception er) {
                er.printStackTrace();
            }

            try {
                fileReader.close();
            } catch (Exception er) {
                er.printStackTrace();
            }
        }

    }

}
