package edu.junit5.quickstart;

import java.io.*;
import java.util.Scanner;

public class ReadFile {
    static String textFile;
     static String enText = "";
     static int zahl = 1;
    public static String getTextFile() throws Exception {
    textFile = filein();

    return textFile;
    }


    public static String filein() throws Exception {
    String fileName = "Test.txt";

    File file = new File ("E:/Downloads/EncryptTest/Test.txt");
    Scanner scan = new Scanner(file);
    String inputText = "";

    while (scan.hasNextLine()){
        inputText = inputText.concat(scan.nextLine());
    }


    return inputText;
}
    public static void fileou() throws Exception {

        if(enText.length() == 0) {
            FileWriter writer = new FileWriter("E:/Downloads/EncryptTest/newFile.txt");
            writer.write(AESEncrypt.getEncryptText());
            writer.close();
        }

        else {
            FileWriter writer = new FileWriter("E:/Downloads/EncryptTest/newFil"+zahl+".txt");
            writer.write(AESEncrypt.getEncryptText());
            writer.close();
            zahl = zahl + 1 ;
        }

        enText = AESEncrypt.getEncryptText();

    }
    //https://www.baeldung.com/java-cipher-input-output-stream
    public static void main(String[] args) throws Exception {





    }
}
