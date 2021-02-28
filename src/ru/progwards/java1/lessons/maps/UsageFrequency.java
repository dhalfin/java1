package ru.progwards.java1.lessons.maps;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class UsageFrequency {
    private String file;

    public void processFile(String fileName) {
        try (FileReader reader = new FileReader(fileName)) {
            StringBuilder sb = new StringBuilder();
            while (reader.ready()) {
                sb.append((char) reader.read());
            }
            file = sb.toString();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public Map<Character, Integer> getLetters() {
        char[] charsArray = getFilterString(file).toCharArray();
        Map<Character, Integer> treeMap = new HashMap<>();
        for (int i = 0; i < charsArray.length; i++) {
            if (treeMap.containsKey(charsArray[i])) {
                int quantity = treeMap.get(charsArray[i]) + 1;
                treeMap.put(charsArray[i], quantity);
            }
            treeMap.putIfAbsent(charsArray[i], 1);
        }
        return treeMap;
    }

    public Map<String, Integer> getWords() {
        String[] strArray = file.split("[^0-9a-zA-Zа-яА-Я]+");
        Map<String, Integer> treeMap = new HashMap<>();
        for (int i = 1; i < strArray.length; i++) {
            if (treeMap.containsKey(strArray[i])) {
                int quantity = treeMap.get(strArray[i]) + 1;
                treeMap.put(strArray[i], quantity);
            }
            treeMap.putIfAbsent(strArray[i], 1);
        }
        return treeMap;
    }


    private String getFilterString(String str) {
        StringBuilder sb = new StringBuilder();
        for (char ch : str.toCharArray()) {
            if (Character.isAlphabetic(ch) || Character.isDigit(ch))
                sb.append(ch);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        UsageFrequency uf = new UsageFrequency();
        uf.processFile("D:/Progwards/Academy/Lessons/src/ru/progwards/java1/lessons/maps/wiki.train.tokens");
        for (var entry : uf.getWords().entrySet()) {
            System.out.println(entry);
        }

    }
}

