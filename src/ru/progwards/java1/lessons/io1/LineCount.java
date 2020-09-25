package ru.progwards.java1.lessons.io1;

import java.io.FileReader;
import java.util.Scanner;

public class LineCount {

    public static int calcEmpty(String fileName) {

        int res = 0;
        int resErrCase = -1;
        String empStr = "";

        try {
            FileReader reader = new FileReader(fileName);
            try {
                Scanner sc = new Scanner(reader);
                while (sc.hasNextLine()) {
                    if (sc.nextLine().compareTo(empStr) == 0) {
                        res++;
                    }
                }
            } finally {
                reader.close();
            }
        } catch (Exception e) {
            return resErrCase;
        }
        return res;
    }
}
