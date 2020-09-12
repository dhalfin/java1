package ru.progwards.java1.lessons.arrays;

import java.util.Arrays;

public class ArraySort {

    public static void sort(int[] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[i] < a[j]) {
                    int temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }
        //System.out.println(Arrays.toString(a));
    }

    public static void main(String[] args) {
        int[] array = {-500, 20067, 0, 5, 10};
        sort(array);
        System.out.println(Arrays.toString(array));
    }
}
