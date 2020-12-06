package ru.progwards.java2.lessons.generics;

public class DynamicArray<T> {

    private T[] array;
    private int numberOfItems;
    private int sizeOfArray = 16;
    private int percentageIncrease = 100;

    DynamicArray() {
        init();
    }

    DynamicArray(int size) {
        this.sizeOfArray = size;
        init();
    }

    DynamicArray(int size, int increase) {
        this.sizeOfArray = size;
        this.percentageIncrease = increase > 1 ? increase : 1;
        init();
    }

    private void init() {
        numberOfItems = 0;
        array = (T[]) new Object[sizeOfArray];
    }

    private void increment() {
        int renewedSize = sizeOfArray + sizeOfArray * percentageIncrease / 100;
        if (renewedSize == sizeOfArray) {
            renewedSize++;
        }
        T[] renewedArray = (T[]) new Object[renewedSize];
        System.arraycopy(array, 0, renewedArray, 0, sizeOfArray);
        array = renewedArray;
        sizeOfArray = renewedSize;
    }

    public void add(T item) {
        if (numberOfItems == sizeOfArray) {
            increment();
        }
        array[numberOfItems++] = item;
    }

    private void checkPosition(int pos) {
        if (pos >= numberOfItems) {
            throw new RuntimeException("Позиция " + pos + " больше, чем длина " + numberOfItems + " массива");
        }
        if (pos < 0) {
            throw new RuntimeException("Позиция " + pos + " меньше индекса первого элемента");
        }
    }


    public void insert(int pos, T item) {
        if (pos > numberOfItems) {
            throw new RuntimeException("Позиция " + pos + " больше, чем длина " + numberOfItems + " массива");
        }
        if (pos < 0) {
            throw new RuntimeException("Позиция " + pos + " меньше индекса первого элемента");
        }
        if (numberOfItems == sizeOfArray) {
            increment();
        }
        for (int i = numberOfItems; i > pos; i--) {
            array[i] = array[i - 1];
            array[pos] = item;
            numberOfItems++;
        }
    }

    public void remove(int pos) {
        checkPosition(pos);
        System.arraycopy(array, pos + 1, array, pos, numberOfItems - pos - 1);
        numberOfItems--;
    }

    public T get(int pos) {
        checkPosition(pos);
        return array[pos];
    }

    public int size() {
        return sizeOfArray;
    }

//    public int length() {
//        return numberOfItems;
//    }
}
