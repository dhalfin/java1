package ru.progwards.java1.lessons.maps;

import java.io.FileReader;
import java.util.*;

public class UsageFrequency {

    private ArrayList<String> words = new ArrayList<>();
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
        Map<Character, Integer> letterMeter = new HashMap<>();
        for (String str : words) {
            for (char a : str.toCharArray()) {
                if (letterMeter.containsKey(a)) {
                    int cnt = letterMeter.get(a);
                    letterMeter.put(a, cnt + 1);
                } else {
                    letterMeter.put(a, 1);
                }
            }
        }
        return letterMeter;
    }

    public Map<String, Integer> getWords() {
        for (String word : str.split("[^a-zA-Z_а-яА-Я_0-9]")) {
            word = word.trim();
            if (getWords.containsKey(word)) {
                getWords.replace(word, getWords.get(word) + 1);
                continue;
            }
            getWords.put(word, 1);
        }
        getWords.remove("");
        return getWords;
    }

    public static void main(String[] args) {
        UsageFrequency uf = new UsageFrequency();
        uf.processFile("D:/Progwards/Academy/Lessons/src/ru/progwards/java1/lessons/maps/wiki.train.tokens");
        for (var entry : uf.getWords().entrySet()) {
            System.out.println(entry);
        }

    }
}

