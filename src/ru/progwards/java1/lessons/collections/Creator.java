package ru.progwards.java1.lessons.collections;

import java.util.*;

public class Creator {

    public static Collection<Integer> fillEven(int n) {
        List<Integer> integers = new ArrayList<>();
        for (int i = 2; i <= n * 2; i++) {
            if (i % 2 == 0) {
                integers.add(i);
            }
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
        for (int i = 0; i < n * 3; i += 3) {
            integers.add(i);
            int cnt = 0;
            for (int j = i * i; cnt < 2; j *= i) {
                integers.add(j);
                cnt++;
            }
        }
        return integers;
    }
}
