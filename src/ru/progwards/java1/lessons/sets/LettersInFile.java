package ru.progwards.java1.lessons.sets;

import java.io.FileReader;
import java.util.Scanner;
import java.util.TreeSet;

public class LettersInFile {

    public static String process(String fileName) throws Exception {
        String outputStr = "";
        TreeSet<Character> characters = new TreeSet<>();
        FileReader reader = new FileReader(fileName);
        Scanner sc = new Scanner(reader);
        while (sc.hasNextLine()) {
            char[] a = sc.nextLine().toCharArray();
            for (char b : a) {
                if (Character.isAlphabetic(b)) {
                    characters.add(b);
                }
            }
        }

        for (Character ch : characters) {
            outputStr += ch;
        }
        return outputStr;
    }
}

