package ru.progwards.java1.lessons.collections;

import java.util.*;

public class Creator {

    public static Collection<Integer> fillEven(int n) {
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            integers.add((i + 2) * 2);
        }
        return integers;
    }

    public static Collection<Integer> fillOdd(int n) {
        List<Integer> integers = new ArrayList<>();
        for (int i = n; i > 0; i--) {
            integers.add(i * 2 - 1);
        }
        return integers;
    }

    public static Collection<Integer> fill3(int n) {
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            integers.add(i * 3, i);
            integers.add(i * 3 + 1, i * i);
            integers.add(i * 3 + 2, i * i * i);
        }
        return integers;
    }
}
