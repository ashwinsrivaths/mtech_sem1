package com.uis.serialization;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class TestSerialization implements Serializable {

    String str = "Hello, World!";
    transient int transientInt = 42; // This field will not be serialized

    @Override
    public String toString() {
        return "TestSerialization{" +
                "str='" + str + '\'' +
                '}';
    }

    public static void main(String[] args) {

        try {
            TestSerialization test = new TestSerialization();
            test.str = "testing serialization in java";
            // test.writeObject(test);
            TestSerialization deserializedTest = test.readObject();

            System.out.println("Deserialized Object: " + deserializedTest);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    private void writeObject(TestSerialization test) throws IOException {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(new File("./test.ser")));
            oos.writeObject(test);
        } catch (IOException e) {
            // TODO: handle exception
            System.out.println("Error during serialization: " + e.getMessage());
        } finally {
            try {
                oos.close();
            } catch (Exception e) {
                System.out.println("Error closing ObjectOutputStream: " + e.getMessage());
            }
        }
    }

    private TestSerialization readObject() {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(new File("./test.ser")));
            TestSerialization test = (TestSerialization) ois.readObject();
            return test;
        } catch (Exception e) {
            System.out.println("Error during deserialization: " + e.getMessage());
            return null;
        } finally {
            try {
                ois.close();
            } catch (Exception e) {
                System.out.println("Error during deserialization: " + e.getMessage());

            }
        }
    }
}
