package ru.progwards.java1.lessons.io1;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CharFilter {

    public static void filterFile(String inFileName, String outFileName, String filter) throws IOException {

        FileReader reader = new FileReader(inFileName);
        FileWriter writer = new FileWriter(outFileName);

        char[] charFilArr = filter.toCharArray();
        int sym;
        while ((sym = reader.read()) != -1) {
            int n = 0;
            for (int i = 0; i < charFilArr.length; i++) {
                if (charFilArr[i] == (char) sym) {
                    n += 1;
                }
            }
            if (n == 0) {
                writer.write(sym);
            }
        }
            reader.close();
            writer.close();
    }

}

