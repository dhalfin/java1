package ru.progwards.java2.lessons.generics;

import java.util.Arrays;

public class ArraySort {

    public static<T extends Comparable<T>> void sort(T[] array) {

        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i].compareTo(array[j]) > 0) {
                    T temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        Double[] arr = {12.03, -7.54, 0.0, 500.009, - 107.9};
        sort(arr);
        System.out.println(Arrays.toString(arr));


        String[] arrStr = {"Yan", "Refactoring", "_27FullTime", "Sort"};
        sort(arrStr);
        System.out.println(Arrays.toString(arrStr));
    }
}
