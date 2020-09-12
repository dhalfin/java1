package ru.progwards.java1.lessons.arrays;

import java.util.Arrays;

public class DIntArray {

    private int[] arrOfInts = {};

    public DIntArray() {

    }

    public void add(int num) {
        int[] newArr = Arrays.copyOf(arrOfInts, arrOfInts.length + 1);
        newArr[newArr.length - 1] = num;
        arrOfInts = newArr;
    }

    public void atInsert(int pos, int num) {
        if (pos <= arrOfInts.length) {
            int[] newArr = new int[arrOfInts.length + 1];
            System.arraycopy(arrOfInts, 0, newArr, 0, pos);
            newArr[pos] = num;
            System.arraycopy(arrOfInts, pos, newArr, pos + 1, arrOfInts.length - pos);
            arrOfInts = newArr;
            System.out.println(Arrays.toString(arrOfInts));
        } else {
            System.out.println("There is no element at the specified index in the array");
        }
    }

    public void atDelete(int pos) {
        if (pos <= arrOfInts.length) {
            int[] newArr = new int[arrOfInts.length - 1];
            System.arraycopy(arrOfInts, 0, newArr, 0, pos);
            System.arraycopy(arrOfInts, pos + 1, newArr, pos, newArr.length - pos);
            arrOfInts = newArr;
            System.out.println(Arrays.toString(arrOfInts));
        } else {
            System.out.println("There is no element at the specified index in the array");
        }
    }

    public int at(int pos) {
        if (pos < arrOfInts.length) {
            System.out.println(arrOfInts[pos]);
            return arrOfInts[pos];
        } else {
            return 0;
        }
    }
}
