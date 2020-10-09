package ru.progwards.java1.lessons.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Finder {
    public static Collection<Integer> findMinSumPair(Collection<Integer> numbers) {
        ArrayList<Integer> arrList = (ArrayList<Integer>) numbers;
        List<Integer> outputList = new ArrayList<>();
        outputList.add(arrList.get(0));
        outputList.add(arrList.get(1));
        Integer sum = arrList.get(0) + arrList.get(1);
        int firstIndex = 0;
        int secondIndex = 1;

        for (int i = 1; i < arrList.size() - 1; i++) {
            if (arrList.get(i) + arrList.get(i + 1) < sum) {
                sum = arrList.get(i) + arrList.get(i + 1);
                outputList.set(0, arrList.get(i));
                outputList.set(1, arrList.get(i + 1));
                firstIndex = i;
                secondIndex = i + 1;
            }
        }
        outputList.set(0, firstIndex);
        outputList.set(1, secondIndex);
        return outputList;
    }

    public static Collection<Integer> findLocalMax(Collection<Integer> numbers) {
        ArrayList<Integer> arrList = (ArrayList<Integer>) numbers;
        List<Integer> outputList = new ArrayList<>();

        for (int i = 1; i < arrList.size() - 1; i++) {
            if (arrList.get(i) > arrList.get(i - 1) && arrList.get(i) > arrList.get(i + 1)) {
                outputList.add(arrList.get(i));
            }
        }
        return outputList;
    }

    public static boolean findSequence(Collection<Integer> numbers) {
        for (int i = 1; i <= numbers.size(); i++) {
            if (numbers.contains(i)) {
                continue;
            } else
                return false;
        }
        return true;
    }

    public static String findSimilar(Collection<String> names) {
        int cnt = 1;
        int maxCnt = 1;
        String maxRepeatingElem = ((List<String>)names).get(0);
        for (int i = 1; i < names.size(); i++) {
            if (((List<String>)names).get(i) == ((List<String>)names).get(i - 1)) {
                cnt++;
                if (cnt > maxCnt) {
                    maxRepeatingElem = ((List<String>)names).get(i);
                    maxCnt = cnt;
                }
            }
            if (((List<String>)names).get(i) != ((List<String>)names).get(i - 1)) {
                cnt = 1;
            }
        }
        return maxRepeatingElem + ":" + maxCnt;
    }
}
