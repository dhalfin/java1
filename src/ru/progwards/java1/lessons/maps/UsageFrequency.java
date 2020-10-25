package ru.progwards.java1.lessons.maps;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class UsageFrequency {

    Map<Character, Integer> getLetters = new HashMap<>();
    Map<String, Integer> getWords = new TreeMap<>();

    String str = "";

    public void processFile(String fileName) {
        try (FileReader fr = new FileReader(fileName)) {
            try (Scanner sc = new Scanner(fr)) {
                while (sc.hasNextLine()) {
                    str += sc.nextLine() + " ";
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Map<Character, Integer> getLetters() {
        char[] characters = str.toCharArray();
        for (char ch : characters) {
            if (Character.isLetterOrDigit(ch)) {
                if (getLetters.containsKey(ch)) {
                    getLetters.replace(ch, getLetters().get(ch) + 1);
                    continue;
                }
                getLetters.put(ch, 1);
            }
        }
        return getLetters;
    }

    public Map<String, Integer> getWords() {
        for (String word : str.split("[^a-zA-Z_а-яА-Я_0-9]")) {
            word = word.trim();
            if (getWords.containsKey(word)) {
                getWords.replace(word, getWords.get(word) + 1);
                continue;
            }
            getWords.put(word,1);
        }
        getWords.remove("");
        return getWords;
    }

    public static void main(String[] args) {
        UsageFrequency uf = new UsageFrequency();
        uf.processFile("D:/Progwards/Academy/Lessons/src/ru/progwards/java1/lessons/maps/wiki.train.tokens");
        for (var entry : uf.getWords().entrySet()){
            System.out.println(entry);
        }

    }
}

